import java.util.Hashtable;
import java.util.Scanner;
import java.util.Stack;

//Clase para la creacion del arbol
public class Tree {
    //Propiedad raiz
    Node raiz;
    //Constructor
    public Tree(String expresion){
        //Inicializamos raiz en null
        this.raiz=null;
        //Ejecutamos el metodo crear arbol mendandole la expresion
        this.CreateTree(expresion);
    }

    //Metodo vacio para la creacion del arbol con el parametro de la cadena incertada por el usuario
    public void CreateTree(String expresion){
        //Creacion de una tabla de hash para igualarlo a la tabla creada
        Hashtable<String,Integer> prioridades=new Hashtable<>();
        prioridades=Tree.Hash();
        //Creacion de la pila de expresiones
        Stack<Node> Expresiones=new Stack<>();
        //Creacion de la pila de Operadores
        Stack<String> Operadores=new Stack<>();

        //Ciclo para el recorrido caracter por caracter
        for(int i=0;i<expresion.length();i++){
            //Comprobamos se el dato iterado es un Operador o parentesis
            if(expresion.charAt(i)=='*'||expresion.charAt(i)=='+'||expresion.charAt(i)=='-'||expresion.charAt(i)=='/'||expresion.charAt(i)=='^'||expresion.charAt(i)=='('||expresion.charAt(i)==')'){
                //Comprobamos si es cualquiera de los operadores
                if(expresion.charAt(i)=='*'||expresion.charAt(i)=='+'||expresion.charAt(i)=='-'||expresion.charAt(i)=='/'||expresion.charAt(i)=='^'){
                   //Si la pila de operadores es vacia
                    if(Operadores.isEmpty()||Operadores.get(Operadores.size()-1).equals("(")||Operadores.get(Operadores.size()-1).equals(")")){
                        //Se inserta el dato u Operadores
                        Operadores.push(String.valueOf(expresion.charAt(i)));
                    }else{
                        //En caso contrario que exista un dato en la pila de Operandos
                        String valor=Operadores.get(Operadores.size()-1);// Obtenemos la cadena del valor de la pila
                        int valor1=prioridades.get(valor);//Obtenemos el valor de la tabla de hash respeccto a la llave
                        int valor2=prioridades.get(String.valueOf(expresion.charAt(i))); //Obtenemos el valor de la tabla de hash respeccto a la llave
                        //Comprobamos que el valor 1 sea menor que el valor de la expresion sea menor que el de la pila
                        if(valor2<=valor1){
                            //Realizamos un ciclo y paramos hasta que el vaor 2 sea mayor al valor 1 o se encuentre un parentesis de apertura
                            while(valor2<=valor1||Operadores.get(Operadores.size()-1).equals("(")){
                                    //Creacion de un objeto de tipo Node
                                    Node newNode=new Node(Operadores.pop());
                                    newNode.derecho=Expresiones.pop(); //Obtenemos lo que hay en expresiones y lo podemos al lado derecho del hijo
                                    newNode.izquierdo=Expresiones.pop();//Obtenemos lo que hay en expresiones y lo podemos al lado derecho del izquierdo
                                    Expresiones.push(newNode);
                                    //Si la pila de Operadores no es vacia ni exista un parentesis de apertura
                                    if(!Operadores.isEmpty()&&!Operadores.get(Operadores.size()-1).equals("(")){
                                        //Asignamos valores
                                        valor=Operadores.get(Operadores.size()-1);
                                        valor1=prioridades.get(valor);
                                    }else{
                                        //Caso contrario rompemos el ciclo
                                        break;
                                    }
                            }
                            //cuando terminemos de sacar a todos los operadores de menor prioridad metemos el caracter
                            Operadores.push(String.valueOf(expresion.charAt(i)));
                        //Si el valor 2 es mayor agregamos el valor a la pila ya que la pila debe de ir de mayor a menor prioridad
                        }else{
                            //Agregamos el operador de mayor prioridad si el ultimo es menor al caracter que se itera
                            Operadores.push(String.valueOf(expresion.charAt(i)));
                        }
                    }
                }else{
                    //si el valor es un parentesis de Apertura entramos el condicional
                    if(expresion.charAt(i)=='('){
                        //Automaticamente se agrega
                        Operadores.push(String.valueOf(expresion.charAt(i)));
                    //Caso contrario Parentesis de cierre
                    }else if(expresion.charAt(i)==')'){
                        /*Realizamos un ciclo ya que dentro de los parentesis puede haber mas de un operador
                        y tenemos que sacar cada uno de ellos siempre y cuando sea distinto de vacio*/
                        while(!Operadores.get(Operadores.size()-1).equals("(")){
                            //Se crea el nodo
                            Node newNode=new Node(Operadores.pop());
                            newNode.derecho=Expresiones.pop();
                            newNode.izquierdo=Expresiones.pop();
                            //Se agrega el nodo a la pila de Expresiones
                            Expresiones.push(newNode);
                            //Comprobamos si el ultimo de la pila
                            if(Operadores.get(Operadores.size()-1).equals("(")){
                                Operadores.pop();
                                //Comprobamos se la pila esta vacia con el metodo vacio
                                this.vacio(Operadores,newNode);
                                //Salimos
                                break;
                            }
                        }

                    }
                }
                //Si el valor es un operando directamente van a la pila de Expresiones
            }else{
                Node newNode=new Node(String.valueOf(expresion.charAt(i)));
                Expresiones.push(newNode);
                this.vacio(Operadores,newNode); //Verficar si la pila es vacia
            }
        }

        //Realiamos un ciclo siempre y cuando la plila de Operadores no este vacia
        while(!Operadores.isEmpty()){
            Node newNode=new Node(Operadores.pop());
            newNode.derecho=Expresiones.pop();
            newNode.izquierdo=Expresiones.pop();
            Expresiones.push(newNode);
            //Si la pila esta vacia el ultimo Nodo es la raiz
            if(Operadores.isEmpty()){
                this.raiz=newNode;
            }
        }

    }

    public void vacio(Stack<String> Operadores,Node node){
        //Si la pila Operadores es vacio
        if(Operadores.isEmpty()){
            //La raiz es el nodo que creamos
            this.raiz=node;
        }
    }


    //Todo los recorridos
    public void PreOrden(Node node){
        if(node!=null){
            System.out.print(node.valor+"\t");
            this.PreOrden(node.izquierdo);
            this.PreOrden(node.derecho);
        }
    }
    public void InOrden(Node node){
        if(node!=null){
            this.InOrden(node.izquierdo);
            System.out.print(node.valor+"\t");
            this.InOrden(node.derecho);
        }
    }
    public void PostOrden(Node node){
        if(node!=null){
            this.PostOrden(node.izquierdo);
            this.PostOrden(node.derecho);
            System.out.print(node.valor+"\t");
        }
    }
    //Todo Fin de los recorridos


    //Metodo main
    public static void main(String[] args) {
        //Variable de tipo String
       String expresion;
       //Instancia de la clase Scanner de java
        Scanner entrada=new Scanner(System.in);
        //Ingreso del valor por consola
        System.out.println("Ingrese su expresion a evaluar");
        expresion=entrada.nextLine();

        //Evaluamos que la cadena este escrita de forma correcta
        if(Evaluacion(expresion)){
            //Instancia de la clase Tree
            Tree arbol=new Tree(expresion);
            //Recorridos
            System.out.println("Recorrido Preorden");
            arbol.PreOrden(arbol.raiz);
            System.out.println();
            System.out.println("Recorrido Inorden");
            arbol.InOrden(arbol.raiz);
            System.out.println();
            System.out.println("Recorrido Postorden");
            arbol.PostOrden(arbol.raiz);
            System.out.println("\n\n");
            //Crear el arbol para graficar pasandole la instacia del arbol
            Poner graficar=new Poner(arbol);
            //Graficamos y lo hacemos visible
            graficar.setVisible(true);
        }else{
            //Caso contrario mandamos un error y no se construye el arbol
            System.out.println("Expresion incorrecta");
        }
    }

    //Metdo estatico que evalua la cadena
    public static boolean Evaluacion(String cadena){
        //Creamos la pila para verificar si es expresion es correcta
        Stack<Character> pila=new Stack<Character>();

        //Recorrido de la pila
        for(int i=0;i<cadena.length();i++){
            if(cadena.charAt(i)=='{'){
                pila.push(cadena.charAt(i));
            }else if(cadena.charAt(i)=='['){
                pila.push(cadena.charAt(i));
            }else if(cadena.charAt(i)=='('){
                pila.push(cadena.charAt(i));
            }else if(cadena.charAt(i)=='}'){
                if(!pila.isEmpty()){
                    if(pila.get(pila.size()-1)=='{'){
                        pila.pop();
                    }
                }
            }else if(cadena.charAt(i)==']'){
                if(!pila.isEmpty()){
                    if(pila.get(pila.size()-1)=='['){
                        pila.pop();
                    }
                }
            }else if(cadena.charAt(i)==')'){
                if (!pila.isEmpty()){
                    if(pila.get(pila.size()-1)=='('){
                        pila.pop();
                    }
                }else{
                    return false;
                }
            }
        }

        //Verifica si la pila esta vacia
        if(pila.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    //Metodo estatico de la creacion de la tabla de hash que retorna un HashTable
    public static Hashtable<String,Integer> Hash(){
        //Creacion de la tabla de hash
        Hashtable<String,Integer> hash=new Hashtable<>();
        //Añadimos datos al hashTable
        hash.put("^",3);
        hash.put("*",2);
        hash.put("/",2);
        hash.put("+",1);
        hash.put("-",1);
        return hash; //Retorno de la tabla
    }


}
