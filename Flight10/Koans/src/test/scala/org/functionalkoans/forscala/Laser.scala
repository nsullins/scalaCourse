package lab{
  package awesome{
    package laser{

      class Gun(val wattage: Int){
        def shoot(): Beam = {
          new Beam
        }

        class Beam{
          def lumens = 10 * wattage
        }
      }
    }
  }
}