package strategy_pattern

                //접근점
class Character(private val weapon: Weapon) {

    fun attack() {
        //Delegate
        weapon.attack()
    }

}