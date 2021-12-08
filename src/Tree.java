import java.util.Hashtable;
import java.util.Scanner;
import java.util.Stack;

public class Tree {
    Node raiz;
    public Tree(String expresion){
        this.raiz=null;
        this.CreateTree(expresion);
    }

    public void CreateTree(String expresion){
        Hashtable<String,Integer> prioridades=new Hashtable<>();
        prioridades=Tree.Hash();
        Stack<Node> Expresiones=new Stack<>();
        Stack<String> Operadores=new Stack<>();
        for(int i=0;i<expresion.length();i++){
            if(expresion.charAt(i)=='*'||expresion.charAt(i)=='+'||expresion.charAt(i)=='-'||expresion.charAt(i)=='/'||expresion.charAt(i)=='^'||expresion.charAt(i)=='('||expresion.charAt(i)==')'){
                if(expresion.charAt(i)=='*'||expresion.charAt(i)=='+'||expresion.charAt(i)=='-'||expresion.charAt(i)=='/'||expresion.charAt(i)=='^'){
                    if(Operadores.isEmpty()||Operadores.get(Operadores.size()-1).equals("(")||Operadores.get(Operadores.size()-1).equals(")")){
                        Operadores.push(String.valueOf(expresion.charAt(i)));
                    }else{
                        String valor=Operadores.get(Operadores.size()-1);
                        int valor1=prioridades.get(valor);
                        int valor2=prioridades.get(String.valueOf(expresion.charAt(i)));
                        if(valor2<=valor1){
                            while(valor2<=valor1||Operadores.get(Operadores.size()-1).equals("(")){
                                    Node newNode=new Node(Operadores.pop());
                                    newNode.derecho=Expresiones.pop();
                                    newNode.izquierdo=Expresiones.pop();
                                    Expresiones.push(newNode);
                                    if(!Operadores.isEmpty()&&!Operadores.get(Operadores.size()-1).equals("(")){
                                        valor=Operadores.get(Operadores.size()-1);
                                        valor1=prioridades.get(valor);
                                    }else{
                                        break;
                                    }
                            }
                            Operadores.push(String.valueOf(expresion.charAt(i)));
                        }else{
                            Operadores.push(String.valueOf(expresion.charAt(i)));
                        }
                    }
                }else{
                    if(expresion.charAt(i)=='('){
                        Operadores.push(String.valueOf(expresion.charAt(i)));
                    }else if(expresion.charAt(i)==')'){
                      Node newNode=new Node(Operadores.pop());
                      Operadores.pop();
                      newNode.derecho=Expresiones.pop();
                      newNode.izquierdo=Expresiones.pop();
                      Expresiones.push(newNode);
                      this.vacio(Operadores,newNode);
                    }
                }
            }else{
                Node newNode=new Node(String.valueOf(expresion.charAt(i)));
                Expresiones.push(newNode);
                this.vacio(Operadores,newNode);
            }
        }

        if(!Operadores.isEmpty()){
            Node newNode=new Node(Operadores.pop());
            newNode.derecho=Expresiones.pop();
            newNode.izquierdo=Expresiones.pop();
            this.raiz=newNode;
        }
    }

    public void vacio(Stack<String> Operadores,Node node){
        if(Operadores.isEmpty()){
            this.raiz=node;
        }
    }


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

    public static void main(String[] args) {
       String expresion;
        Scanner entrada=new Scanner(System.in);
        System.out.println("Ingrese su expresion a evaluar");
        expresion=entrada.nextLine();

        if(Evaluacion(expresion)){
            Tree arbol=new Tree(expresion);
            System.out.println("Recorrido Preorden");
            arbol.PreOrden(arbol.raiz);
            System.out.println();
            System.out.println("Recorrido Inorden");
            arbol.InOrden(arbol.raiz);
            System.out.println();
            System.out.println("Recorrido Postorden");
            arbol.PostOrden(arbol.raiz);
            System.out.println("\n\n");
            //Crear el arbol
            Poner graficar=new Poner(arbol);
            graficar.setVisible(true);
        }else{
            System.out.println("Expresion incorrecta");
        }
    }

    public static boolean Evaluacion(String cadena){
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

    public static Hashtable<String,Integer> Hash(){
        Hashtable<String,Integer> hash=new Hashtable<>();
        hash.put("^",3);
        hash.put("*",2);
        hash.put("/",2);
        hash.put("+",1);
        hash.put("-",1);
        return hash;
    }


}
