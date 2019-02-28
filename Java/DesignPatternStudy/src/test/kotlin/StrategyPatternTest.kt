import StrategyPattern.Bow
import StrategyPattern.Character
import StrategyPattern.Sword
import org.assertj.core.api.Assertions.*
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class StrategyPatternTest {

    val outContent = ByteArrayOutputStream()

    @Before
    fun setUp() {
        System.setOut(PrintStream(outContent))
    }

    @Test
    fun SwordCharacterTest() {
        Character(Sword()).attack()
        assertThat(outContent.toString()).isEqualTo("검 공격!")
    }

    @Test
    fun BowCharacterTest() {
        Character(Bow()).attack()
        assertThat(outContent.toString()).isEqualTo("활 공격!")
    }

}