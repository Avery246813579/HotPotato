package me.avery246813579.hotpotato.handlers;

import java.lang.reflect.Method;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkHandler{
  private Method world_getHandle = null;
  private Method nms_world_broadcastEntityEffect = null;
  private Method firework_getHandle = null;

  public void playFirework(World world, Location loc, FireworkEffect fe) throws Exception{
    Firework fw = (Firework)world.spawn(loc, Firework.class);

    Object nms_world = null;
    Object nms_firework = null;

    if (this.world_getHandle == null){
      this.world_getHandle = getMethod(world.getClass(), "getHandle");
      this.firework_getHandle = getMethod(fw.getClass(), "getHandle");
    }

    nms_world = this.world_getHandle.invoke(world, null);
    nms_firework = this.firework_getHandle.invoke(fw, null);

    if (this.nms_world_broadcastEntityEffect == null){
      this.nms_world_broadcastEntityEffect = getMethod(nms_world.getClass(), 
        "broadcastEntityEffect");
    }

    FireworkMeta data = fw.getFireworkMeta();

    data.clearEffects();

    data.setPower(1);

    data.addEffect(fe);

    fw.setFireworkMeta(data);

    this.nms_world_broadcastEntityEffect.invoke(nms_world, new Object[] { nms_firework, Byte.valueOf((byte) 17) });

    fw.remove();
  }

  private static Method getMethod(Class<?> cl, String method) {
    for (Method m : cl.getMethods()) {
      if (m.getName().equals(method)) {
        return m;
      }
    }
    return null;
  }

  public FireworkEffect getRandomEffect() {
    Random generator = new Random();
    int type = generator.nextInt(9) + 1;
    if (type == 1)
      return FireworkEffect.builder().with(FireworkEffect.Type.BALL)
        .withColor(Color.RED).withFade(Color.BLUE).build();
    if (type == 2)
      return FireworkEffect.builder().with(FireworkEffect.Type.BALL)
        .withColor(Color.BLUE).withFade(Color.GREEN).build();
    if (type == 3)
      return FireworkEffect.builder().with(FireworkEffect.Type.BALL)
        .withColor(Color.GREEN).withFade(Color.RED).build();
    if (type == 4)
      return FireworkEffect.builder().with(FireworkEffect.Type.BURST)
        .withColor(Color.RED).withFade(Color.GREEN).build();
    if (type == 5)
      return FireworkEffect.builder().with(FireworkEffect.Type.BURST)
        .withColor(Color.BLUE).withFade(Color.RED).build();
    if (type == 6)
      return FireworkEffect.builder().with(FireworkEffect.Type.BURST)
        .withColor(Color.GREEN).withFade(Color.BLUE).build();
    if (type == 7)
      return FireworkEffect.builder().with(FireworkEffect.Type.STAR)
        .withColor(Color.RED).withFade(Color.BLUE).build();
    if (type == 8)
      return FireworkEffect.builder().with(FireworkEffect.Type.STAR)
        .withColor(Color.BLUE).withFade(Color.GREEN).build();
    if (type == 9) {
      return FireworkEffect.builder().with(FireworkEffect.Type.STAR)
        .withColor(Color.GREEN).withFade(Color.RED).build();
    }
    return FireworkEffect.builder().with(FireworkEffect.Type.STAR)
      .withColor(Color.BLUE).withFade(Color.RED).build();
  }

  public FireworkEffect getBlowupRandomEffect()
  {
    Random generator = new Random();
    int type = generator.nextInt(6) + 1;
    if (type == 1)
      return FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE)
        .withColor(Color.RED).withFade(Color.GREEN).build();
    if (type == 2)
      return FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE)
        .withColor(Color.BLUE).withFade(Color.RED).build();
    if (type == 3)
      return FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE)
        .withColor(Color.GREEN).withFade(Color.BLUE).build();
    if (type == 4)
      return FireworkEffect.builder().with(FireworkEffect.Type.CREEPER)
        .withColor(Color.RED).withFade(Color.BLUE).build();
    if (type == 5)
      return FireworkEffect.builder().with(FireworkEffect.Type.CREEPER)
        .withColor(Color.BLUE).withFade(Color.GREEN).build();
    if (type == 6) {
      return FireworkEffect.builder().with(FireworkEffect.Type.CREEPER)
        .withColor(Color.GREEN).withFade(Color.RED).build();
    }
    return FireworkEffect.builder().with(FireworkEffect.Type.CREEPER)
      .withColor(Color.BLUE).withFade(Color.RED).build();
  }
}