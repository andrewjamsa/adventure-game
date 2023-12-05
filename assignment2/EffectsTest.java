import AdventureModel.AdventureObject;
import AdventureModel.Effects.*;
import AdventureModel.Player;
import AdventureModel.Room;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * A mock class that implements EffectStrategy. for the sake of testing.
 */
class EffectStrategyMock implements EffectStrategy{
    String desc;
    int health;

    EffectStrategyMock(String desc, int health){
        this.desc = desc;
        this.health = health;
    }

    @Override
    public void doEffect(Player player) {
        player.setHealth(health);
    }

    @Override
    public String getDescription() {
        return desc;
    }
}

public class EffectsTest {
    /*
    Testing the factory
     */
    @Test
    void effectFactoryTest(){
        EffectStrategy effect = EffectFactory.generateEffect("[\"damageEffect\", -1]");
        assertTrue(effect instanceof DamageEffect);
    }

    /*
    Testing the factory recursive
     */
    @Test
    void effectFactoryRecursiveTest(){
        EffectStrategy effect = EffectFactory.generateEffect("[\"multipleEffects\", [\"damageEffect\", -1], [\"damageEffect\", -2]]");
        assertTrue(effect instanceof MultipleEffects);
        assertEquals("Damages you 1 health everytime you move, Damages you 2 health everytime you move", effect.getDescription());
    }

    /*
    Testing the EffectStrategyMock class
     */
    @Test
    void effectStrategyMockTest(){
        Player player = new Player(new Room("test", 1, "testroomdesc", "testname"), 5, 100);
        EffectStrategyMock effect = new EffectStrategyMock("TestDescEffectStrategy", 10);
        effect.doEffect(player);
        assertEquals(10, player.getHealthValue());
        assertEquals("TestDescEffectStrategy", effect.getDescription());
    }

    /*
    Testing the MultipleEffects class
     */
    @Test
    void multipleEffectsTest(){
        Player player = new Player(new Room("test", 1, "testroomdesc", "testname"), 5, 100);
        EffectStrategyMock effect1 = new EffectStrategyMock("TestDescEffectStrategy", 10);
        EffectStrategyMock effect2 = new EffectStrategyMock("TestDescEffectStrategy2", 15);
        EffectStrategy multiEffect = new MultipleEffects(Arrays.asList(effect1,effect2));
        multiEffect.doEffect(player);
        assertEquals(15, player.getHealthValue());
        assertEquals("TestDescEffectStrategy, TestDescEffectStrategy2", multiEffect.getDescription());
    }

    /*
    Testing the HideableEffect class
     */
    @Test
    void hidableEffectsTest(){
        Player player = new Player(new Room("test", 1, "testroomdesc", "testname"), 5, 100);
        EffectStrategyMock effect1 = new EffectStrategyMock("TestDescEffectStrategy", 10);
        HideableEffect hideEffect = new HideableEffect(effect1);
        hideEffect.doEffect(player);
        assertEquals(10, player.getHealthValue());
        assertEquals("???", hideEffect.getDescription());
        hideEffect.setHide(false);
        assertEquals("TestDescEffectStrategy", hideEffect.getDescription());
    }

    /*
    Testing the DamageEffect class
     */
    @Test
    void damageEffectsTest(){
        Player player = new Player(new Room("test", 1, "testroomdesc", "testname"), 20, 100);
        EffectStrategy hideEffect = new DamageEffect(-5);
        hideEffect.doEffect(player);
        assertEquals(15, player.getHealthValue());
        assertEquals("Damages you 5 health everytime you move.", hideEffect.getDescription());
    }

    /*
    Test the GiveItemEffect class
     */
    @Test
    void giveItemEffectsTest(){
        Player player = new Player(new Room("test", 1, "testroomdesc", "testname"), 20, 100);
        AdventureObject object = new AdventureObject("TestObject","TestObjectDesc", null);
        EffectStrategy effect = new GiveItemEffect(object);
        effect.doEffect(player);
        assertTrue(player.inventory.contains(object));
    }
}
