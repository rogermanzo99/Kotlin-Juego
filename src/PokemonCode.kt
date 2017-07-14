import java.util.*
/**
 * Created by root on 13/07/17.
 */

enum class Pokemones(val id: Int){Pikachu(1),Charmander(2), Squirtle(3), Charisar(4)}


fun main (args: Array<String>){

    println("Elige un pokémon:")

    for ((index, p) in Pokemones.values().withIndex()){
        println("${index + 1}) $p")
    }

    val scanner = Scanner(System.`in`)
    val opcion = scanner.nextInt()

    val pokemon : Pokemon = generarPokemon(opcion)

    mostrarDatos(pokemon)

    val random = Random()
    val numeroAlAzar = 1 + random.nextInt(4)
    val pokemonSalvaje: Pokemon = generarPokemon(numeroAlAzar)

    println("Un ${pokemonSalvaje.nombre} salvaje ha aparecido! \n")

    do {

        println("${pokemon.nombre} HP:${pokemon.hp} | ${pokemonSalvaje.nombre} HP:${pokemonSalvaje.hp}")

        println("Elige un ataque:")

        for((index, a) in pokemon.listaAtaques.withIndex()){
            println("${index}: ${a.nombre}")
        }

        val ataqueSeleccionado = scanner.nextInt()

        if(procesarAtaque(pokemon,pokemonSalvaje, ataqueSeleccionado)){
            break
        }

        val ataqueAleatorio : Int = 1 + random.nextInt(pokemonSalvaje.listaAtaques.size)

        if(procesarAtaque(pokemonSalvaje,pokemon,ataqueAleatorio)){
            break
        } else {
            println("Los dos pokemones siguen en pie!")
            println("..continuamos!")
        }

    }while (pokemon.hp > 0 && pokemonSalvaje.hp > 0)
}

fun procesarAtaque(pokemonAtacante: Pokemon, pokemonDefensor: Pokemon, ataqueSeleccionado: Int): Boolean {

    val ataque = pokemonAtacante.obtenerAtaque(ataqueSeleccionado)
    println("${pokemonAtacante.nombre} ha usado ${ataque.nombre}")

    val valorDanho = calcularDanho(pokemonAtacante.ataque, pokemonAtacante.defensa, ataque)
    println("${pokemonDefensor.nombre} ha recibido $valorDanho puntos de daño!")
    pokemonDefensor.hp -= valorDanho

    if (pokemonDefensor.hp <= 0){
        println("${pokemonDefensor.nombre} se agoto!")
        println("${pokemonAtacante.nombre} gano la batalla!")

        return true
    }

    return false
}

fun  calcularDanho(valorAtaque: Int, valorDefensa: Int, ataque: Ataque): Int
        = ((((2*1 + 10.0)/ 250)*(valorAtaque / valorDefensa)*ataque.poder + 2)* 1.5).toInt()

fun mostrarDatos(pokemon: Pokemon) {
    println("Has elegido a ${pokemon.nombre} \nHP:${pokemon.hp}\nATAQUE:${pokemon.ataque}\nDEFENSA:${pokemon.defensa}\n")
}

fun generarPokemon(opcion: Int): Pokemon = when(opcion){
    Pokemones.Charisar.id -> Pokemon("Charisar",45,49,50, arrayOf(Ataque("Lava de fuego",50),Ataque("Candela",40)))
    Pokemones.Squirtle.id -> Pokemon("Squirtle",39,52,43, arrayOf(Ataque("Aplastar",50),Ataque("Garra",40)))
    Pokemones.Charmander.id -> Pokemon("Charmander",55,59,60, arrayOf(Ataque("Congelar",50),Ataque("Agua kill",50)))
    Pokemones.Pikachu.id -> Pokemon("Pikachu",65,62,65, arrayOf(Ataque("Impact trueno",60),Ataque("Electrizar",40)))
    else ->Pokemon("Missingo",33,130,0, arrayOf(Ataque("pay day",20), Ataque("blind",25)))

}