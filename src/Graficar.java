

import java.awt.*;
import java.util.*;
import javax.swing.*;


public class Graficar extends JPanel
{
    private Tree miArbol;
    private HashMap posicionNodos = null;
    private HashMap subtreeSizes = null;
    private boolean dirty = true;
    private int parent2child = 20, child2child = 30;
    private Dimension empty = new Dimension(0,0);
    private FontMetrics fm = null;



    public Graficar(Tree miArbol)
    {
        this.miArbol = miArbol;
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(220, 60));
        posicionNodos = new HashMap();
        subtreeSizes = new HashMap();
        dirty = true;
        add(panel);
        repaint();
    }


    /**
     * Calcula las posiciones de los respectivos subárboles y de cada nodo que
     * forma parte de ese subárbol, para conocer en que posición van a ir dibujados
     * los rectángulos representativos del árbol de la expresión.
     */
    private void calcularPosiciones()
    {
        posicionNodos.clear();
        subtreeSizes.clear();
        Node root = this.miArbol.raiz;
        if (root != null)
        {
            calcularTamañoSubarbol(root);
            calcularPosicion(root, Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
        }
    }


    private Dimension calcularTamañoSubarbol(Node n)
    {
        if (n == null)
            return new Dimension(0,0);

        Dimension ld = calcularTamañoSubarbol(n.izquierdo);
        Dimension rd = calcularTamañoSubarbol(n.derecho);

        int h = fm.getHeight() + parent2child + Math.max(ld.height, rd.height);
        int w = ld.width + child2child + rd.width;

        Dimension d = new Dimension(w, h);
        subtreeSizes.put(n, d);

        return d;
    }



    private void calcularPosicion(Node n, int left, int right, int top)
    {
        if (n == null)
            return;

        Dimension ld = (Dimension) subtreeSizes.get(n.izquierdo);
        if (ld == null)
            ld = empty;

        Dimension rd = (Dimension) subtreeSizes.get(n.derecho);
        if (rd == null)
            rd = empty;

        int center = 0;

        if (right != Integer.MAX_VALUE)
            center = right - rd.width - child2child/2;
        else if (left != Integer.MAX_VALUE)
            center = left + ld.width + child2child/2;
        int width = fm.stringWidth(n.valor+"");

        posicionNodos.put(n,new Rectangle(center - width/2 - 3, top, width + 6, fm.getHeight()));

        calcularPosicion(n.izquierdo, Integer.MAX_VALUE, center - child2child/2, top + fm.getHeight() + parent2child);
        calcularPosicion(n.derecho, center + child2child/2, Integer.MAX_VALUE, top + fm.getHeight() + parent2child);
    }


    private void dibujarArbol(Graphics2D g, Node n, int puntox, int puntoy, int yoffs)
    {
        if (n == null)
            return;

        Rectangle r = (Rectangle) posicionNodos.get(n);
        g.draw(r);
        g.drawString(n.valor+"", r.x + 3, r.y + yoffs);

        if (puntox != Integer.MAX_VALUE)

            g.drawLine(puntox, puntoy, (int)(r.x + r.width/2), r.y);

        dibujarArbol(g, n.izquierdo, (int)(r.x + r.width/2), r.y + r.height, yoffs);
        dibujarArbol(g, n.derecho, (int)(r.x + r.width/2), r.y + r.height, yoffs);

    }



    public void paint(Graphics g)
    {
        super.paint(g);

        fm = g.getFontMetrics();
        if (dirty)
        {
            calcularPosiciones();
            dirty = false;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(getWidth() / 2, parent2child);
        dibujarArbol(g2d, this.miArbol.raiz, Integer.MAX_VALUE, Integer.MAX_VALUE,
                fm.getLeading() + fm.getAscent());
        fm = null;
    }

}





