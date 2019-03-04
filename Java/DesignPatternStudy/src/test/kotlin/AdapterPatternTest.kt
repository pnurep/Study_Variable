import adapter_pattern.Adapter
import adapter_pattern.AdapterImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class AdapterPatternTest {

    @Test
    fun twice() {
        val adapter: Adapter = AdapterImpl()
        assertThat(adapter.twiceOf(100f)).isEqualTo(200f)
    }

    @Test
    fun half() {
        val adapter: Adapter = AdapterImpl()
        assertThat(adapter.halfOf(100f)).isEqualTo(50f)
    }

}