package shadersmod.client;

import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderProgramData {

public static final int EaZy = 2016;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public int programIDGL;
	public int uniform_texture;
	public int uniform_lightmap;
	public int uniform_normals;
	public int uniform_specular;
	public int uniform_shadow;
	public int uniform_watershadow;
	public int uniform_shadowtex0;
	public int uniform_shadowtex1;
	public int uniform_depthtex0;
	public int uniform_depthtex1;
	public int uniform_shadowcolor;
	public int uniform_shadowcolor0;
	public int uniform_shadowcolor1;
	public int uniform_noisetex;
	public int uniform_gcolor;
	public int uniform_gdepth;
	public int uniform_gnormal;
	public int uniform_composite;
	public int uniform_gaux1;
	public int uniform_gaux2;
	public int uniform_gaux3;
	public int uniform_gaux4;
	public int uniform_colortex0;
	public int uniform_colortex1;
	public int uniform_colortex2;
	public int uniform_colortex3;
	public int uniform_colortex4;
	public int uniform_colortex5;
	public int uniform_colortex6;
	public int uniform_colortex7;
	public int uniform_gdepthtex;
	public int uniform_depthtex2;
	public int uniform_tex;
	public int uniform_heldItemId;
	public int uniform_heldBlockLightValue;
	public int uniform_fogMode;
	public int uniform_fogColor;
	public int uniform_skyColor;
	public int uniform_worldTime;
	public int uniform_moonPhase;
	public int uniform_frameTimeCounter;
	public int uniform_sunAngle;
	public int uniform_shadowAngle;
	public int uniform_rainStrength;
	public int uniform_aspectRatio;
	public int uniform_viewWidth;
	public int uniform_viewHeight;
	public int uniform_near;
	public int uniform_far;
	public int uniform_sunPosition;
	public int uniform_moonPosition;
	public int uniform_upPosition;
	public int uniform_previousCameraPosition;
	public int uniform_cameraPosition;
	public int uniform_gbufferModelView;
	public int uniform_gbufferModelViewInverse;
	public int uniform_gbufferPreviousProjection;
	public int uniform_gbufferProjection;
	public int uniform_gbufferProjectionInverse;
	public int uniform_gbufferPreviousModelView;
	public int uniform_shadowProjection;
	public int uniform_shadowProjectionInverse;
	public int uniform_shadowModelView;
	public int uniform_shadowModelViewInverse;
	public int uniform_wetness;
	public int uniform_eyeAltitude;
	public int uniform_eyeBrightness;
	public int uniform_eyeBrightnessSmooth;
	public int uniform_terrainTextureSize;
	public int uniform_terrainIconSize;
	public int uniform_isEyeInWater;
	public int uniform_hideGUI;
	public int uniform_centerDepthSmooth;
	public int uniform_atlasSize;

	public ShaderProgramData(final int programID) {
		programIDGL = programID;
		uniform_texture = ARBShaderObjects.glGetUniformLocationARB(programID, "texture");
		uniform_lightmap = ARBShaderObjects.glGetUniformLocationARB(programID, "lightmap");
		uniform_normals = ARBShaderObjects.glGetUniformLocationARB(programID, "normals");
		uniform_specular = ARBShaderObjects.glGetUniformLocationARB(programID, "specular");
		uniform_shadow = ARBShaderObjects.glGetUniformLocationARB(programID, "shadow");
		uniform_watershadow = ARBShaderObjects.glGetUniformLocationARB(programID, "watershadow");
		uniform_shadowtex0 = ARBShaderObjects.glGetUniformLocationARB(programID, "shadowtex0");
		uniform_shadowtex1 = ARBShaderObjects.glGetUniformLocationARB(programID, "shadowtex1");
		uniform_depthtex0 = ARBShaderObjects.glGetUniformLocationARB(programID, "depthtex0");
		uniform_depthtex1 = ARBShaderObjects.glGetUniformLocationARB(programID, "depthtex1");
		uniform_shadowcolor = ARBShaderObjects.glGetUniformLocationARB(programID, "shadowcolor");
		uniform_shadowcolor0 = ARBShaderObjects.glGetUniformLocationARB(programID, "shadowcolor0");
		uniform_shadowcolor1 = ARBShaderObjects.glGetUniformLocationARB(programID, "shadowcolor1");
		uniform_noisetex = ARBShaderObjects.glGetUniformLocationARB(programID, "noisetex");
		uniform_gcolor = ARBShaderObjects.glGetUniformLocationARB(programID, "gcolor");
		uniform_gdepth = ARBShaderObjects.glGetUniformLocationARB(programID, "gdepth");
		uniform_gnormal = ARBShaderObjects.glGetUniformLocationARB(programID, "gnormal");
		uniform_composite = ARBShaderObjects.glGetUniformLocationARB(programID, "composite");
		uniform_gaux1 = ARBShaderObjects.glGetUniformLocationARB(programID, "gaux1");
		uniform_gaux2 = ARBShaderObjects.glGetUniformLocationARB(programID, "gaux2");
		uniform_gaux3 = ARBShaderObjects.glGetUniformLocationARB(programID, "gaux3");
		uniform_gaux4 = ARBShaderObjects.glGetUniformLocationARB(programID, "gaux4");
		uniform_colortex0 = ARBShaderObjects.glGetUniformLocationARB(programID, "colortex0");
		uniform_colortex1 = ARBShaderObjects.glGetUniformLocationARB(programID, "colortex1");
		uniform_colortex2 = ARBShaderObjects.glGetUniformLocationARB(programID, "colortex2");
		uniform_colortex3 = ARBShaderObjects.glGetUniformLocationARB(programID, "colortex3");
		uniform_colortex4 = ARBShaderObjects.glGetUniformLocationARB(programID, "colortex4");
		uniform_colortex5 = ARBShaderObjects.glGetUniformLocationARB(programID, "colortex5");
		uniform_colortex6 = ARBShaderObjects.glGetUniformLocationARB(programID, "colortex6");
		uniform_colortex7 = ARBShaderObjects.glGetUniformLocationARB(programID, "colortex7");
		uniform_gdepthtex = ARBShaderObjects.glGetUniformLocationARB(programID, "gdepthtex");
		uniform_depthtex2 = ARBShaderObjects.glGetUniformLocationARB(programID, "depthtex2");
		uniform_tex = ARBShaderObjects.glGetUniformLocationARB(programID, "tex");
		uniform_heldItemId = ARBShaderObjects.glGetUniformLocationARB(programID, "heldItemId");
		uniform_heldBlockLightValue = ARBShaderObjects.glGetUniformLocationARB(programID, "heldBlockLightValue");
		uniform_fogMode = ARBShaderObjects.glGetUniformLocationARB(programID, "fogMode");
		uniform_fogColor = ARBShaderObjects.glGetUniformLocationARB(programID, "fogColor");
		uniform_skyColor = ARBShaderObjects.glGetUniformLocationARB(programID, "skyColor");
		uniform_worldTime = ARBShaderObjects.glGetUniformLocationARB(programID, "worldTime");
		uniform_moonPhase = ARBShaderObjects.glGetUniformLocationARB(programID, "moonPhase");
		uniform_frameTimeCounter = ARBShaderObjects.glGetUniformLocationARB(programID, "frameTimeCounter");
		uniform_sunAngle = ARBShaderObjects.glGetUniformLocationARB(programID, "sunAngle");
		uniform_shadowAngle = ARBShaderObjects.glGetUniformLocationARB(programID, "shadowAngle");
		uniform_rainStrength = ARBShaderObjects.glGetUniformLocationARB(programID, "rainStrength");
		uniform_aspectRatio = ARBShaderObjects.glGetUniformLocationARB(programID, "aspectRatio");
		uniform_viewWidth = ARBShaderObjects.glGetUniformLocationARB(programID, "viewWidth");
		uniform_viewHeight = ARBShaderObjects.glGetUniformLocationARB(programID, "viewHeight");
		uniform_near = ARBShaderObjects.glGetUniformLocationARB(programID, "near");
		uniform_far = ARBShaderObjects.glGetUniformLocationARB(programID, "far");
		uniform_sunPosition = ARBShaderObjects.glGetUniformLocationARB(programID, "sunPosition");
		uniform_moonPosition = ARBShaderObjects.glGetUniformLocationARB(programID, "moonPosition");
		uniform_upPosition = ARBShaderObjects.glGetUniformLocationARB(programID, "upPosition");
		uniform_previousCameraPosition = ARBShaderObjects.glGetUniformLocationARB(programID, "previousCameraPosition");
		uniform_cameraPosition = ARBShaderObjects.glGetUniformLocationARB(programID, "cameraPosition");
		uniform_gbufferModelView = ARBShaderObjects.glGetUniformLocationARB(programID, "gbufferModelView");
		uniform_gbufferModelViewInverse = ARBShaderObjects.glGetUniformLocationARB(programID,
				"gbufferModelViewInverse");
		uniform_gbufferPreviousProjection = ARBShaderObjects.glGetUniformLocationARB(programID,
				"gbufferPreviousProjection");
		uniform_gbufferProjection = ARBShaderObjects.glGetUniformLocationARB(programID, "gbufferProjection");
		uniform_gbufferProjectionInverse = ARBShaderObjects.glGetUniformLocationARB(programID,
				"gbufferProjectionInverse");
		uniform_gbufferPreviousModelView = ARBShaderObjects.glGetUniformLocationARB(programID,
				"gbufferPreviousModelView");
		uniform_shadowProjection = ARBShaderObjects.glGetUniformLocationARB(programID, "shadowProjection");
		uniform_shadowProjectionInverse = ARBShaderObjects.glGetUniformLocationARB(programID,
				"shadowProjectionInverse");
		uniform_shadowModelView = ARBShaderObjects.glGetUniformLocationARB(programID, "shadowModelView");
		uniform_shadowModelViewInverse = ARBShaderObjects.glGetUniformLocationARB(programID, "shadowModelViewInverse");
		uniform_wetness = ARBShaderObjects.glGetUniformLocationARB(programID, "wetness");
		uniform_eyeAltitude = ARBShaderObjects.glGetUniformLocationARB(programID, "eyeAltitude");
		uniform_eyeBrightness = ARBShaderObjects.glGetUniformLocationARB(programID, "eyeBrightness");
		uniform_eyeBrightnessSmooth = ARBShaderObjects.glGetUniformLocationARB(programID, "eyeBrightnessSmooth");
		uniform_terrainTextureSize = ARBShaderObjects.glGetUniformLocationARB(programID, "terrainTextureSize");
		uniform_terrainIconSize = ARBShaderObjects.glGetUniformLocationARB(programID, "terrainIconSize");
		uniform_isEyeInWater = ARBShaderObjects.glGetUniformLocationARB(programID, "isEyeInWater");
		uniform_hideGUI = ARBShaderObjects.glGetUniformLocationARB(programID, "hideGUI");
		uniform_centerDepthSmooth = ARBShaderObjects.glGetUniformLocationARB(programID, "centerDepthSmooth");
		uniform_atlasSize = ARBShaderObjects.glGetUniformLocationARB(programID, "atlasSize");
	}
}
