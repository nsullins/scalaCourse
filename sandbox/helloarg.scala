println("hello, " + args(0) + "!")

def max(x: Int, y: Int): Int = if(x > y) x else y

println(max(6,7))

var i = 0
while(i < args.length){
	println(args(i))
	i += 1
}

println()


args.foreach(println)
args foreach println _
args.foreach(println(_))

for(arg <- args) println(arg + "for")




def fib(v: Int): Int = v match{

	case 0 => 0
	case 1 => 1
	case n =>  
}