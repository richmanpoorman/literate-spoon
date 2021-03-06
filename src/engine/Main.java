package engine;

import engine.words.Direction;
import engine.words.Verb;
import engine.words.Word;
import engine.things.Effect;
import engine.things.Object;
import engine.Terminal;

public class Main {

	public static Engine game;

	public static void main(String args[]) {
		game = new Engine();
		game.addWord(new Verb("move go walk run climb jog strut", (Word w, Engine t) -> {
			if (w.getClass() != Direction.class) {
				Terminal.println("Please specify a direction");
				return;
			}

			t.protag.changePos(w.value);

		}, null));
		game.addWord(new Verb("eat consume", null, (Object o, Engine t) -> {
			t.protag.hunger -= o.consumability;
			if (o.consumability < 0) {
				t.protag.effects.add(new Effect((p) -> {
					t.protag.health += o.consumability * 2;
				}, 3, "That was painful to eat."));
			} else {
				Terminal.println("You ate a " + o.accessor + ". Delicious.");
			}
			t.protag.currentRoom.objects.remove(o);
		}));
		game.addWord(new Verb("take steal grab", null, (Object o, Engine t) -> {
			t.protag.inventory.add(o);
			t.protag.currentRoom.objects.remove(o);
		}));

		game.addWord(new Direction("north forwards", "121"));
		game.addWord(new Direction("south backwards", "101"));
		game.addWord(new Direction("east right", "211"));
		game.addWord(new Direction("west left", "011"));
		game.addWord(new Direction("up skyward", "112"));
		game.addWord(new Direction("down groundward", "110"));

		while (true) {

			game.update();
		}

	}
}
