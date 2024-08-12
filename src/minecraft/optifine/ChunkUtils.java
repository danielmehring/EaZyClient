package optifine;

import net.minecraft.world.chunk.Chunk;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

public class ChunkUtils {

public static final int EaZy = 1880;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static Field fieldHasEntities = null;
	private static boolean fieldHasEntitiesMissing = false;

	public static boolean hasEntities(final Chunk chunk) {
		if (fieldHasEntities == null) {
			if (fieldHasEntitiesMissing) {
				return true;
			}

			fieldHasEntities = findFieldHasEntities(chunk);

			if (fieldHasEntities == null) {
				fieldHasEntitiesMissing = true;
				return true;
			}
		}

		try {
			return fieldHasEntities.getBoolean(chunk);
		} catch (final Exception var2) {
			Config.warn("Error calling Chunk.hasEntities");
			Config.warn(var2.getClass().getName() + " " + var2.getMessage());
			fieldHasEntitiesMissing = true;
			return true;
		}
	}

	private static Field findFieldHasEntities(final Chunk chunk) {
		try {
			final ArrayList e = new ArrayList();
			final ArrayList listBoolValuesPre = new ArrayList();
			final Field[] fields = Chunk.class.getDeclaredFields();

			for (final Field listBoolValuesTrue : fields) {
				if (listBoolValuesTrue.getType() == Boolean.TYPE) {
					listBoolValuesTrue.setAccessible(true);
					e.add(listBoolValuesTrue);
					listBoolValuesPre.add(listBoolValuesTrue.get(chunk));
				}
			}

			chunk.setHasEntities(false);
			final ArrayList var13 = new ArrayList();
			final Iterator var14 = e.iterator();

			while (var14.hasNext()) {
				final Field listMatchingFields = (Field) var14.next();
				var13.add(listMatchingFields.get(chunk));
			}

			chunk.setHasEntities(true);
			final ArrayList var15 = new ArrayList();
			final Iterator var16 = e.iterator();
			Field field;

			while (var16.hasNext()) {
				field = (Field) var16.next();
				var15.add(field.get(chunk));
			}

			final ArrayList var17 = new ArrayList();

			for (int var18 = 0; var18 < e.size(); ++var18) {
				final Field field1 = (Field) e.get(var18);
				final Boolean valFalse = (Boolean) var13.get(var18);
				final Boolean valTrue = (Boolean) var15.get(var18);

				if (!valFalse && valTrue) {
					var17.add(field1);
					final Boolean valPre = (Boolean) listBoolValuesPre.get(var18);
					field1.set(chunk, valPre);
				}
			}

			if (var17.size() == 1) {
				field = (Field) var17.get(0);
				return field;
			}
		} catch (final Exception var12) {
			Config.warn(var12.getClass().getName() + " " + var12.getMessage());
		}

		Config.warn("Error finding Chunk.hasEntities");
		return null;
	}
}
