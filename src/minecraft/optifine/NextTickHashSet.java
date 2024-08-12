package optifine;

import net.minecraft.util.BlockPos;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.NextTickListEntry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.collect.Iterators;

public class NextTickHashSet extends TreeSet {

public static final int EaZy = 1939;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final LongHashMap longHashMap = new LongHashMap();
	private int minX = Integer.MIN_VALUE;
	private int minZ = Integer.MIN_VALUE;
	private int maxX = Integer.MIN_VALUE;
	private int maxZ = Integer.MIN_VALUE;

	public NextTickHashSet(final Set oldSet) {
		final Iterator it = oldSet.iterator();

		while (it.hasNext()) {
			final Object obj = it.next();
			add(obj);
		}
	}

	@Override
	public boolean contains(final Object obj) {
		if (!(obj instanceof NextTickListEntry)) {
			return false;
		} else {
			final NextTickListEntry entry = (NextTickListEntry) obj;
			final Set set = this.getSubSet(entry, false);
			return set == null ? false : set.contains(entry);
		}
	}

	@Override
	@SuppressWarnings("unused")
	public boolean add(final Object obj) {
		if (!(obj instanceof NextTickListEntry)) {
			return false;
		} else {
			final NextTickListEntry entry = (NextTickListEntry) obj;

			if (entry == null) {
				return false;
			} else {
				final Set set = this.getSubSet(entry, true);
				final boolean added = set.add(entry);
				final boolean addedParent = super.add(obj);

				if (added != addedParent) {
					throw new IllegalStateException("Added: " + added + ", addedParent: " + addedParent);
				} else {
					return addedParent;
				}
			}
		}
	}

	@Override
	public boolean remove(final Object obj) {
		if (!(obj instanceof NextTickListEntry)) {
			return false;
		} else {
			final NextTickListEntry entry = (NextTickListEntry) obj;
			final Set set = this.getSubSet(entry, false);

			if (set == null) {
				return false;
			} else {
				final boolean removed = set.remove(entry);
				final boolean removedParent = super.remove(entry);

				if (removed != removedParent) {
					throw new IllegalStateException("Added: " + removed + ", addedParent: " + removedParent);
				} else {
					return removedParent;
				}
			}
		}
	}

	private Set getSubSet(final NextTickListEntry entry, final boolean autoCreate) {
		if (entry == null) {
			return null;
		} else {
			final BlockPos pos = entry.field_180282_a;
			final int cx = pos.getX() >> 4;
			final int cz = pos.getZ() >> 4;
			return this.getSubSet(cx, cz, autoCreate);
		}
	}

	private Set getSubSet(final int cx, final int cz, final boolean autoCreate) {
		final long key = ChunkCoordIntPair.chunkXZ2Int(cx, cz);
		HashSet set = (HashSet) longHashMap.getValueByKey(key);

		if (set == null && autoCreate) {
			set = new HashSet();
			longHashMap.add(key, set);
		}

		return set;
	}

	@Override
	public Iterator iterator() {
		if (minX == Integer.MIN_VALUE) {
			return super.iterator();
		} else if (size() <= 0) {
			return Iterators.emptyIterator();
		} else {
			final int cMinX = minX >> 4;
			final int cMinZ = minZ >> 4;
			final int cMaxX = maxX >> 4;
			final int cMaxZ = maxZ >> 4;
			final ArrayList listIterators = new ArrayList();

			for (int x = cMinX; x <= cMaxX; ++x) {
				for (int z = cMinZ; z <= cMaxZ; ++z) {
					final Set set = this.getSubSet(x, z, false);

					if (set != null) {
						listIterators.add(set.iterator());
					}
				}
			}

			if (listIterators.size() <= 0) {
				return Iterators.emptyIterator();
			} else if (listIterators.size() == 1) {
				return (Iterator) listIterators.get(0);
			} else {
				return Iterators.concat(listIterators.iterator());
			}
		}
	}

	public void setIteratorLimits(final int minX, final int minZ, final int maxX, final int maxZ) {
		this.minX = Math.min(minX, maxX);
		this.minZ = Math.min(minZ, maxZ);
		this.maxX = Math.max(minX, maxX);
		this.maxZ = Math.max(minZ, maxZ);
	}

	public void clearIteratorLimits() {
		minX = Integer.MIN_VALUE;
		minZ = Integer.MIN_VALUE;
		maxX = Integer.MIN_VALUE;
		maxZ = Integer.MIN_VALUE;
	}
}
