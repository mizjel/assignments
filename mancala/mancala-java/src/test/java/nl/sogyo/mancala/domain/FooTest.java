package nl.sogyo.mancala.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class FooTest {

    @Test
    public void the_answer_to_life_the_universe_and_everything_is_42()
    {
        var foo = new Foo();
        var answer = foo.theAnswerToLifeTheUniverseAndEverything();
        assertEquals(42, answer);
    }
}
