package br.UFSC.GRIMA.entidades.features;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.util.findPoints.LimitedCircle;
import br.UFSC.GRIMA.util.findPoints.LimitedElement;

public class CircularBoss extends Boss
{
	private double diametro1;
	private double diametro2;
	private Point3d center;
	
	public CircularBoss()
	{
//		this.createGeometricalElements();
	}
	
	public CircularBoss(String nome, double x, double y, double z, double diametro1, double diametro2, double altura)
	{
		super(altura);
		this.diametro1 = diametro1;
		this.diametro2 = diametro2;
		this.setPosicao(x, y, z);
		this.setCenter(new Point3d(x, y, z));
		this.createGeometricalElements();
	}
	public double getDiametro1() 
	{
		return diametro1;
	}

	public void setDiametro1(double diametro1) {
		this.diametro1 = diametro1;
	}

	public double getDiametro2() {
		return diametro2;
	}

	public void setDiametro2(double diametro2) {
		this.diametro2 = diametro2;
	}
	public void createGeometricalElements() 
	{
		this.geometricalElements.add(new LimitedCircle(new Point3d(this.X, this.Y, this.Z), this.diametro1 / 2));
	}
	/**
	 * Este atributo e o centro em relacao a posicao do Boss (e nao a posicao absoluta!!)
	 * @return
	 */
	public Point3d getCenter() {
		return center;
	}

	public void setCenter(Point3d center) {
		this.center = center;
	}

	public DefaultMutableTreeNode getNodo() {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Circular Boss -" + this.getIndice());
		nodo.add(new DefaultMutableTreeNode("Nome: " + this.getNome()));
		nodo.add(new DefaultMutableTreeNode("Diameter 1 : " + this.getDiametro1()));
		nodo.add(new DefaultMutableTreeNode("Diameter 2 : " + this.getDiametro2()));
		nodo.add(new DefaultMutableTreeNode("Posicao X, Y, Z  : " + this.X + ", " + this.Y + ", " + this.Z));
		nodo.add(new DefaultMutableTreeNode("Altura : " + this.getAltura()));
		nodo.add(new DefaultMutableTreeNode("Rugosidade : " + this.getRugosidade()));
		nodo.add(new DefaultMutableTreeNode("Tolerancia : " + this.getTolerancia()));

		return nodo;
	}
}
