package net.minecraft.client.resources;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

public interface IResourceManager {

   Set func_135055_a();

   IResource func_110536_a(ResourceLocation var1) throws IOException;

   List func_135056_b(ResourceLocation var1) throws IOException;
}
