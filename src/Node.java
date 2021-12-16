public class Node {
    //Propiedades
    String valor;
    //Punteros
    Node izquierdo;
    Node derecho;
    //Metodo constructor
    public Node(String valor){
        this.valor=valor;
        this.derecho=null;
        this.izquierdo=null;
    }
}
