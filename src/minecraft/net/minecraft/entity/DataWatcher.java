package net.minecraft.entity;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Rotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang3.ObjectUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DataWatcher {

	public static final int EaZy = 1105;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private final Entity owner;

	/** When isBlank is true the DataWatcher is not watching any objects */
	private boolean isBlank = true;
	private static final Map dataTypes = Maps.newHashMap();
	private final Map watchedObjects = Maps.newHashMap();

	/** true if one or more object was changed */
	private boolean objectChanged;
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	// private static final String __OBFID = "http://https://fuckuskid00001559";

	public DataWatcher(final Entity owner) {
		this.owner = owner;
	}

	/**
	 * adds a new object to dataWatcher to watch, to update an already existing
	 * object see updateObject. Arguments: data Value Id, Object to add
	 */
	public void addObject(final int id, final Object object) {
		final Integer var3 = (Integer) dataTypes.get(object.getClass());

		if (var3 == null) {
			throw new IllegalArgumentException("Unknown data type: " + object.getClass());
		} else if (id > 31) {
			throw new IllegalArgumentException("Data value id is too big with " + id + "! (Max is " + 31 + ")");
		} else if (watchedObjects.containsKey(id)) {
			throw new IllegalArgumentException("Duplicate id value for " + id + "!");
		} else {
			final DataWatcher.WatchableObject var4 = new DataWatcher.WatchableObject(var3, id, object);
			lock.writeLock().lock();
			watchedObjects.put(id, var4);
			lock.writeLock().unlock();
			isBlank = false;
		}
	}

	/**
	 * Add a new object for the DataWatcher to watch, using the specified data
	 * type.
	 */
	public void addObjectByDataType(final int id, final int type) {
		final DataWatcher.WatchableObject var3 = new DataWatcher.WatchableObject(type, id, (Object) null);
		lock.writeLock().lock();
		watchedObjects.put(id, var3);
		lock.writeLock().unlock();
		isBlank = false;
	}

	/**
	 * gets the bytevalue of a watchable object
	 */
	public byte getWatchableObjectByte(final int id) {
		return ((Byte) getWatchedObject(id).getObject());
	}

	public short getWatchableObjectShort(final int id) {
		return ((Short) getWatchedObject(id).getObject());
	}

	/**
	 * gets a watchable object and returns it as a Integer
	 */
	public int getWatchableObjectInt(final int id) {
		return ((Integer) getWatchedObject(id).getObject());
	}

	public float getWatchableObjectFloat(final int id) {
		return ((Float) getWatchedObject(id).getObject());
	}

	/**
	 * gets a watchable object and returns it as a String
	 */
	public String getWatchableObjectString(final int id) {
		return (String) getWatchedObject(id).getObject();
	}

	/**
	 * Get a watchable object as an ItemStack.
	 */
	public ItemStack getWatchableObjectItemStack(final int id) {
		return (ItemStack) getWatchedObject(id).getObject();
	}

	/**
	 * is threadsafe, unless it throws an exception, then
	 */
	private DataWatcher.WatchableObject getWatchedObject(final int id) {
		lock.readLock().lock();
		DataWatcher.WatchableObject var2;

		try {
			var2 = (DataWatcher.WatchableObject) watchedObjects.get(id);
		} catch (final Throwable var6) {
			final CrashReport var4 = CrashReport.makeCrashReport(var6, "Getting synched entity data");
			final CrashReportCategory var5 = var4.makeCategory("Synched entity data");
			var5.addCrashSection("Data ID", id);
			throw new ReportedException(var4);
		}

		lock.readLock().unlock();
		return var2;
	}

	public Rotations getWatchableObjectRotations(final int id) {
		return (Rotations) getWatchedObject(id).getObject();
	}

	/**
	 * updates an already existing object
	 */
	public void updateObject(final int id, final Object newData) {
		final DataWatcher.WatchableObject var3 = getWatchedObject(id);

		if (ObjectUtils.notEqual(newData, var3.getObject())) {
			var3.setObject(newData);
			owner.func_145781_i(id);
			var3.setWatched(true);
			objectChanged = true;
		}
	}

	public void setObjectWatched(final int id) {
		getWatchedObject(id).watched = true;
		objectChanged = true;
	}

	/**
	 * true if one or more object was changed
	 */
	public boolean hasObjectChanged() {
		return objectChanged;
	}

	/**
	 * Writes the list of watched objects (entity attribute of type {byte,
	 * short, int, float, string, ItemStack, ChunkCoordinates}) to the specified
	 * PacketBuffer
	 */
	public static void writeWatchedListToPacketBuffer(final List objectsList, final PacketBuffer buffer)
			throws IOException {
		if (objectsList != null) {
			final Iterator var2 = objectsList.iterator();

			while (var2.hasNext()) {
				final DataWatcher.WatchableObject var3 = (DataWatcher.WatchableObject) var2.next();
				writeWatchableObjectToPacketBuffer(buffer, var3);
			}
		}

		buffer.writeByte(127);
	}

	public List getChanged() {
		ArrayList var1 = null;

		if (objectChanged) {
			lock.readLock().lock();
			final Iterator var2 = watchedObjects.values().iterator();

			while (var2.hasNext()) {
				final DataWatcher.WatchableObject var3 = (DataWatcher.WatchableObject) var2.next();

				if (var3.isWatched()) {
					var3.setWatched(false);

					if (var1 == null) {
						var1 = Lists.newArrayList();
					}

					var1.add(var3);
				}
			}

			lock.readLock().unlock();
		}

		objectChanged = false;
		return var1;
	}

	public void writeTo(final PacketBuffer buffer) throws IOException {
		lock.readLock().lock();
		final Iterator var2 = watchedObjects.values().iterator();

		while (var2.hasNext()) {
			final DataWatcher.WatchableObject var3 = (DataWatcher.WatchableObject) var2.next();
			writeWatchableObjectToPacketBuffer(buffer, var3);
		}

		lock.readLock().unlock();
		buffer.writeByte(127);
	}

	public List getAllWatched() {
		ArrayList var1 = null;
		lock.readLock().lock();
		DataWatcher.WatchableObject var3;

		for (final Iterator var2 = watchedObjects.values().iterator(); var2.hasNext(); var1.add(var3)) {
			var3 = (DataWatcher.WatchableObject) var2.next();

			if (var1 == null) {
				var1 = Lists.newArrayList();
			}
		}

		lock.readLock().unlock();
		return var1;
	}

	/**
	 * Writes a watchable object (entity attribute of type {byte, short, int,
	 * float, string, ItemStack, ChunkCoordinates}) to the specified
	 * PacketBuffer
	 */
	private static void writeWatchableObjectToPacketBuffer(final PacketBuffer buffer,
			final DataWatcher.WatchableObject object) throws IOException {
		final int var2 = (object.getObjectType() << 5 | object.getDataValueId() & 31) & 255;
		buffer.writeByte(var2);

		switch (object.getObjectType()) {
		case 0:
			buffer.writeByte(((Byte) object.getObject()));
			break;

		case 1:
			buffer.writeShort(((Short) object.getObject()));
			break;

		case 2:
			buffer.writeInt(((Integer) object.getObject()));
			break;

		case 3:
			buffer.writeFloat(((Float) object.getObject()));
			break;

		case 4:
			buffer.writeString((String) object.getObject());
			break;

		case 5:
			final ItemStack var3 = (ItemStack) object.getObject();
			buffer.writeItemStackToBuffer(var3);
			break;

		case 6:
			final BlockPos var4 = (BlockPos) object.getObject();
			buffer.writeInt(var4.getX());
			buffer.writeInt(var4.getY());
			buffer.writeInt(var4.getZ());
			break;

		case 7:
			final Rotations var5 = (Rotations) object.getObject();
			buffer.writeFloat(var5.func_179415_b());
			buffer.writeFloat(var5.func_179416_c());
			buffer.writeFloat(var5.func_179413_d());
		}
	}

	/**
	 * Reads a list of watched objects (entity attribute of type {byte, short,
	 * int, float, string, ItemStack, ChunkCoordinates}) from the supplied
	 * PacketBuffer
	 */
	public static List readWatchedListFromPacketBuffer(final PacketBuffer buffer) throws IOException {
		ArrayList var1 = null;

		for (byte var2 = buffer.readByte(); var2 != 127; var2 = buffer.readByte()) {
			if (var1 == null) {
				var1 = Lists.newArrayList();
			}

			final int var3 = (var2 & 224) >> 5;
			final int var4 = var2 & 31;
			DataWatcher.WatchableObject var5 = null;

			switch (var3) {
			case 0:
				var5 = new DataWatcher.WatchableObject(var3, var4, buffer.readByte());
				break;

			case 1:
				var5 = new DataWatcher.WatchableObject(var3, var4, buffer.readShort());
				break;

			case 2:
				var5 = new DataWatcher.WatchableObject(var3, var4, buffer.readInt());
				break;

			case 3:
				var5 = new DataWatcher.WatchableObject(var3, var4, buffer.readFloat());
				break;

			case 4:
				var5 = new DataWatcher.WatchableObject(var3, var4, buffer.readStringFromBuffer(32767));
				break;

			case 5:
				var5 = new DataWatcher.WatchableObject(var3, var4, buffer.readItemStackFromBuffer());
				break;

			case 6:
				final int var6 = buffer.readInt();
				final int var7 = buffer.readInt();
				final int var8 = buffer.readInt();
				var5 = new DataWatcher.WatchableObject(var3, var4, new BlockPos(var6, var7, var8));
				break;

			case 7:
				final float var9 = buffer.readFloat();
				final float var10 = buffer.readFloat();
				final float var11 = buffer.readFloat();
				var5 = new DataWatcher.WatchableObject(var3, var4, new Rotations(var9, var10, var11));
			}

			var1.add(var5);
		}

		return var1;
	}

	public void updateWatchedObjectsFromList(final List p_75687_1_) {
		lock.writeLock().lock();
		final Iterator var2 = p_75687_1_.iterator();

		while (var2.hasNext()) {
			final DataWatcher.WatchableObject var3 = (DataWatcher.WatchableObject) var2.next();
			final DataWatcher.WatchableObject var4 = (DataWatcher.WatchableObject) watchedObjects
					.get(var3.getDataValueId());

			if (var4 != null) {
				var4.setObject(var3.getObject());
				owner.func_145781_i(var3.getDataValueId());
			}
		}

		lock.writeLock().unlock();
		objectChanged = true;
	}

	public boolean getIsBlank() {
		return isBlank;
	}

	public void func_111144_e() {
		objectChanged = false;
	}

	static {
		dataTypes.put(Byte.class, Integer.valueOf(0));
		dataTypes.put(Short.class, Integer.valueOf(1));
		dataTypes.put(Integer.class, Integer.valueOf(2));
		dataTypes.put(Float.class, Integer.valueOf(3));
		dataTypes.put(String.class, Integer.valueOf(4));
		dataTypes.put(ItemStack.class, Integer.valueOf(5));
		dataTypes.put(BlockPos.class, Integer.valueOf(6));
		dataTypes.put(Rotations.class, Integer.valueOf(7));
	}

	public static class WatchableObject {
		private final int objectType;
		private final int dataValueId;
		private Object watchedObject;
		private boolean watched;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001560";

		public WatchableObject(final int type, final int id, final Object object) {
			dataValueId = id;
			watchedObject = object;
			objectType = type;
			watched = true;
		}

		public int getDataValueId() {
			return dataValueId;
		}

		public void setObject(final Object object) {
			watchedObject = object;
		}

		public Object getObject() {
			return watchedObject;
		}

		public int getObjectType() {
			return objectType;
		}

		public boolean isWatched() {
			return watched;
		}

		public void setWatched(final boolean watched) {
			this.watched = watched;
		}
	}
}
