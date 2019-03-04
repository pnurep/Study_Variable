package adapter_pattern

class AdapterImpl : Adapter {

    override fun halfOf(f: Float): Float = Math.halfOf(f.toDouble()).toFloat()

    override fun twiceOf(f: Float): Float = Math.twiceOf(f.toDouble()).toFloat()

}