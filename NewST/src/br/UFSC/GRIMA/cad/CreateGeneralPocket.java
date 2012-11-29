package br.UFSC.GRIMA.cad;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.visual.CreateGeneralPocketFrame;
import br.UFSC.GRIMA.entidades.features.Face;
import br.UFSC.GRIMA.entidades.features.Feature;
import br.UFSC.GRIMA.entidades.features.GeneralClosedPocket;
import br.UFSC.GRIMA.util.CircularPath;
import br.UFSC.GRIMA.util.projeto.Axis2Placement3D;
import br.UFSC.GRIMA.util.projeto.Projeto;

public class CreateGeneralPocket extends CreateGeneralPocketFrame implements ActionListener, KeyListener
{
	static LinePanel linePanel; 
	private double radius = 0;
	private double profundidade = 0;
	private double posicaoZ = 0;
	private Face face;
	private JanelaPrincipal parent;
	static ArrayList<Point2D> poligonoAuxiliar = new ArrayList<Point2D>();// --> poligono com os vertices arredondados para triangulacao
	boolean isClosedCurve = false;
	private ArrayList<ArrayList<Point2D>> triangles = new ArrayList<ArrayList<Point2D>>();
	private double zoom = 1;
	private static ArrayList<CircularPath> arcos;
	
	public CreateGeneralPocket(JanelaPrincipal parent, Projeto projeto, Face face)
	{
		super(parent);
		this.face = face;
		this.parent = parent;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.button1.addActionListener(this); //Close button
		this.button2.addActionListener(this); //help button
		addKeyListener(this);
		
		linePanel = new LinePanel(projeto);
		linePanel.setFacePrincipal(face.getTipo(), 0);
		this.layeredPane1.setLayout(new BorderLayout());
		this.layeredPane1.add(linePanel);
		
		Toolkit toolKit = Toolkit.getDefaultToolkit();
    	Dimension d = toolKit.getScreenSize();
        setSize((int)(d.width/1.8), (int)(d.height/1.8));
        setLocationRelativeTo(null);
        
        super.radiusSpinner.addChangeListener(new ChangeListener() 
        {
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				radius = (Double)radiusSpinner.getValue();
				ArrayList<Point2D> novaLista = transformPolygonInRoundPolygon(linePanel.pointListCC, radius);
				linePanel.poligono = new GeneralPath();
				linePanel.angulosList = new ArrayList<Double>();
				GeneralPath forma = new GeneralPath();
				
				forma.moveTo(linePanel.pointListCC.get(0).getX(), linePanel.pointListCC.get(0).getY());
				for(int i = 0; i < linePanel.pointListCC.size(); i++)
				{
					forma.lineTo(linePanel.pointListCC.get(i).getX(), linePanel.pointListCC.get(i).getY());
				}
				forma.closePath();
				
				double alfa;
				Point2D p0 = null, p1 = null, p2 = null;
				for (int i = 0; i < linePanel.pointListCC.size(); i++) 
				{
					p0 = linePanel.pointListCC.get(i);
					try 
					{
						p1 = linePanel.pointListCC.get(i - 1);
					} catch (Exception ex) {
						p1 = linePanel.pointListCC.get(linePanel.pointListCC.size() - 1);
					}
					try 
					{
						p2 = linePanel.pointListCC.get(i + 1);
					} catch (Exception ex) 
					{
						p2 = linePanel.pointListCC.get(0);
					}
					alfa = solveAngle(p0, p1, p2, forma, linePanel.pointListCC);			
//					System.err.println("alfa = " + alfa * 180 / Math.PI);
					linePanel.angulosList.add(alfa);
				}

				linePanel.poligono.moveTo(novaLista.get(0).getX() * zoom+ 20, novaLista.get(0).getY() * zoom + 20);

				for(int i = 1; i < novaLista.size(); i++)
				{
					linePanel.poligono.lineTo(novaLista.get(i).getX() * zoom + 20, novaLista.get(i).getY() * zoom + 20);
				}
				linePanel.poligono.closePath();
				linePanel.repaint();
			}
		});
        super.spinnerZoom.addChangeListener(new ChangeListener() 
        {
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				zoom = (Double)spinnerZoom.getValue() / 100;
				linePanel.setZoom(zoom);
				
				linePanel.poligono = new GeneralPath();
				if(linePanel.pointList.size() > 0)
				{
					linePanel.poligono.moveTo(linePanel.pointList.get(0).getX() * zoom + 20, linePanel.pointList.get(0).getY() * zoom	+ 20);

					for (int i = 1; i < linePanel.pointList.size(); i++)
					{
						linePanel.poligono.lineTo(linePanel.pointList.get(i).getX() * zoom + 20, linePanel.pointList.get(i).getY() * zoom + 20);
					}
					if(isClosedCurve)
						linePanel.poligono.closePath();
				}
				linePanel.repaint();
			}
		});
        super.spinnerSeparaGrade.addChangeListener(new ChangeListener() 
        {
			@Override
			public void stateChanged(ChangeEvent arg0) 
			{
				linePanel.separacaoGrade = (Double)spinnerSeparaGrade.getValue();
				linePanel.repaint();
			}
		});
        this.checkBox1.addActionListener(this);
        this.checkBox2.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object o = e.getSource();
		if(o.equals(okButton))
		{
			this.ok();
		} 
		else if(o.equals(cancelButton))
		{
			this.dispose();
		}
		else if(o.equals(button1))
		{
			this.closeCurve();
		}
		else if(o.equals(button2))
		{
			this.help();
		} else if(o.equals(checkBox1))
		{
			selected();
		} else if(o.equals(checkBox2))
		{
			selectedGrid();
		}
	}
	private void selectedGrid() 
	{
		if (this.checkBox2.isSelected())
		{
			linePanel.grade = true;
			repaint();
		}
		else
		{
			linePanel.grade = false;	
			repaint();
		}
	}

	private void selected()
	{
		if (this.checkBox1.isSelected() == true)
		{
			this.spinnerDepth.setEnabled(false);
			double profTmp = face.getProfundidadeMaxima() - ((Double)(spinnerPosZ.getValue())).doubleValue();
			spinnerDepth.setModel(new SpinnerNumberModel(profTmp, null, null, 1.0));
			this.spinnerPosZ.addChangeListener(new ChangeListener() 
			{
				public void stateChanged(ChangeEvent evt) 
				{
					double profTmp = face.getProfundidadeMaxima() - ((Double)(spinnerPosZ.getValue())).doubleValue();
					spinnerDepth.setModel(new SpinnerNumberModel(profTmp, null, null, 1.0));
				}
			});
		}
		else if (this.checkBox1.isSelected() == false)
		{
			this.spinnerDepth.setEnabled(true);
		}
	}
	private void closeCurve() 
	{
		try{
			if(linePanel.pointList.get(0) != linePanel.pointList.get(linePanel.pointList.size() - 1) && linePanel.pointList.size() > 2)
			{
				linePanel.poligono.closePath();
				linePanel.line = false;
				this.button1.setEnabled(false);
				linePanel.removeMouseListener(linePanel);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "O poligono deve ter mais de dois pontos");
			}
			linePanel.pointListCC = transformPolygonInCounterClockPolygon(linePanel.pointList);
			//System.out.println("OR = " + linePanel.pointList2d);
			//System.out.println("CC = " + linePanel.pointList2dCC);
			
			this.radiusSpinner.setEnabled(true);
			isClosedCurve = true;
			linePanel.repaint();
			} catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "Voce ainda nao definiu os pontos do poligono!");
			}
	}
	private void help() 
	{
		
	}
	private void ok() 
	{
		boolean ok = false;
		
		
//		if(this.validateAngles())
//		{
//			ok = true;
//		}
//		else
//		{
//			ok = false;
//		}
		this.posicaoZ = (Double)spinnerPosZ.getValue();
		this.profundidade = (Double)spinnerDepth.getValue();
		if(this.profundidade + posicaoZ < face.getProfundidadeMaxima() && !checkBox1.isSelected())
		{
			ok = true;
		}else if(this.profundidade + posicaoZ == face.getProfundidadeMaxima() && checkBox1.isSelected())
		{
			ok = true;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Profundidade maior do que a profundidade do bloco");
			ok = false;
			return;
		}
		if(radius > 0)
		{
			ok = true;
		}
		else
		{
			ok = false;
			JOptionPane.showMessageDialog(null, "O raio deve ser maior que zero");
		}
		if(ok)
		{
			GeneralPath forma = new GeneralPath();
			ArrayList<Point2D> vertices = CreateGeneralPocket.transformPolygonInRoundPolygon(linePanel.pointListCC, radius);
			forma.moveTo(vertices.get(0).getX(), vertices.get(0).getY());
			for(int i = 1; i < vertices.size(); i++)
			{
				forma.lineTo(vertices.get(i).getX(), vertices.get(i).getY());
			}
			forma.closePath();
			
			GeneralClosedPocket generalPocket = new GeneralClosedPocket();
			generalPocket.setPoints(linePanel.pointListCC);
			generalPocket.setRadius(radius);
			generalPocket.setProfundidade(profundidade);
			generalPocket.setPosicao(linePanel.pointListCC.get(0).getX(), linePanel.pointListCC.get(0).getY(), posicaoZ);
			generalPocket.setNome(this.textField1.getText());
			generalPocket.setForma(forma);
			generalPocket.setPassante(checkBox1.isSelected());
			generalPocket.setRugosidade((Double)spinnerRugosidade.getValue());
			
			if(face.validarFeature(generalPocket))
			{
				Point3d coordinates = null;
				ArrayList<Double> axis = null, refDirection = null;
				if (this.face.getTipo() == Face.XY)
				{
					coordinates = new Point3d(generalPocket.getPoints().get(0).getX(), generalPocket.getPoints().get(0).getY(), this.face.getProfundidadeMaxima() - posicaoZ);
					axis = new ArrayList<Double>();
					axis.add(0.0);
					axis.add(0.0);
					axis.add(1.0);
					
					refDirection = new ArrayList<Double>();
					refDirection.add(1.0);
					refDirection.add(0.0);
					refDirection.add(0.0);
				} else if (this.face.getTipo() == Face.XZ)
				{
					coordinates = new Point3d(generalPocket.getPoints().get(0).getX(), posicaoZ, generalPocket.getPoints().get(0).getY());
					axis = new ArrayList<Double>();
					axis.add(0.0);
					axis.add(-1.0);
					axis.add(0.0);
					
					refDirection = new ArrayList<Double>();
					refDirection.add(1.0);
					refDirection.add(0.0);
					refDirection.add(0.0);
					
					
				} else if (this.face.getTipo() == Face.YX)
				{
					coordinates = new Point3d(generalPocket.getPoints().get(0).getX(), this.face.getLargura() - generalPocket.getPoints().get(0).getY(), face.getProfundidadeMaxima() - posicaoZ);
					axis = new ArrayList<Double>();
					axis.add(0.0);
					axis.add(0.0);
					axis.add(-1.0);
					
					refDirection = new ArrayList<Double>();
					refDirection.add(1.0);
					refDirection.add(0.0);
					refDirection.add(0.0);
					
				} else if (this.face.getTipo() == Face.YZ)
				{
					coordinates = new Point3d(this.face.getProfundidadeMaxima() - posicaoZ, generalPocket.getPoints().get(0).getY(), this.face.getComprimento() - generalPocket.getPoints().get(0).getX());
					axis = new ArrayList<Double>();
					axis.add(1.0);
					axis.add(0.0);
					axis.add(0.0);
					
					refDirection = new ArrayList<Double>();
					refDirection.add(0.0);
					refDirection.add(0.0);
					refDirection.add(-1.0);
					
				} else if (this.face.getTipo() == Face.ZX)
				{
					coordinates = new Point3d(generalPocket.getPoints().get(0).getX(), this.face.getProfundidadeMaxima() - posicaoZ, this.face.getLargura() - generalPocket.getPoints().get(0).getY());
					axis = new ArrayList<Double>();
					axis.add(0.0);
					axis.add(1.0);
					axis.add(0.0);
					
					refDirection = new ArrayList<Double>();
					refDirection.add(1.0);
					refDirection.add(0.0);
					refDirection.add(0.0);
					
				} else if (this.face.getTipo() == Face.ZY)
				{
					coordinates = new Point3d(posicaoZ, generalPocket.getPoints().get(0).getY(), face.getComprimento() - generalPocket.getPoints().get(0).getX());
					axis = new ArrayList<Double>();
					axis.add(-1.0);
					axis.add(0.0);
					axis.add(0.0);
					
					refDirection = new ArrayList<Double>();
					refDirection.add(0.0);
					refDirection.add(0.0);
					refDirection.add(1.0);
					
				}
				Axis2Placement3D position = new Axis2Placement3D(coordinates, axis, refDirection);
				position.setName(generalPocket.getNome() + " placement");
				generalPocket.setPosition(position);	
				try
				{
					Feature mae = generalPocket.getFeaturePrecedente();
					mae.itsSons.add(generalPocket);
				} catch(Exception e)
				{
					
				}
				this.face.addFeature(generalPocket);
				this.parent.desenhador.repaint();
				this.parent.atualizarArvore();
				this.parent.textArea1.setText(this.parent.textArea1.getText() + "\n" +  "General Closed Pocked: " +generalPocket.getNome() + " added with success!");
				
				dispose();
			}

			
		}
	}
	private boolean validateAngles() 
	{
		boolean isValid = false;
		
		Point2D p0, p1, p2;
		for(int i = 0; i < linePanel.pointList.size(); i++)
		{
			try{
				p0 = linePanel.pointList.get(i);
				p1 = linePanel.pointList.get(i - 1);
				p2 = linePanel.pointList.get(i + 1);
			} catch(Exception e)
			{
				if(e.getMessage().equals("-1"))
				{
					p1 = linePanel.pointList.get(linePanel.pointList.size() - 1);
				} else
				{
					p2 = linePanel.pointList.get(0);
				}
			}
			
		}
		return isValid;
	}
	public static double solveAngle(Point2D p0, Point2D p1, Point2D p2, GeneralPath forma, ArrayList<Point2D> pointList)
	{
		double distance0, distance1, distance2;
		double alfa;

		distance0 = p1.distance(p2);
		distance1 = p0.distance(p1);
		distance2 = p2.distance(p0);

		int nPoints = 20;
		double hx = (p2.getX() - p1.getX()) / nPoints;
		double hy = (p2.getY() - p1.getY()) / nPoints;
		boolean allOut;
		
		double v1v2=(p1.getX() - p0.getX()) * (p2.getX() - p0.getX()) + (p1.getY() - p0.getY()) * (p2.getY() - p0.getY());
		alfa = Math.acos(v1v2/(distance1 * distance2));

		int nPointsIn = 0;
		for (int iPoint = 1; iPoint < nPoints - 1; iPoint++) 
		{
			Point2D testPoint = new Point2D.Double((p1.getX() + hx * iPoint),
					(int) (p1.getY() + hy * iPoint));

			if (forma.contains(testPoint)|| pointList.size()==3) 
			{
				nPointsIn++;
			}
		}
		
		if (alfa > Math.PI)
		{
			alfa = alfa - Math.PI;
		}
		
		if (nPointsIn == 0)
		{
			alfa = -alfa;
		}
		
		//System.out.println("alfaAf="+alfa*180/Math.PI);
		return alfa;
	}
//	public static double solveAngle(Point2D p0, Point2D p1, Point2D p2, GeneralPath forma, ArrayList<Point2D> pointList)
//	{
//		double distance0, distance1, distance2;
//		double alfa;
//
//		distance0 = p1.distance(p2);
//		distance1 = p0.distance(p1);
//		distance2 = p2.distance(p0);
//
//		int nPoints = 20;
//		double hx = (p2.getX() - p1.getX()) / nPoints;
//		double hy = (p2.getY() - p1.getY()) / nPoints;
//		boolean allOut;
//		
//		double v1v2=(p1.getX() - p0.getX()) * (p2.getX() - p0.getX()) + (p1.getY() - p0.getY()) * (p2.getY() - p0.getY());
//		alfa = Math.acos(v1v2/(distance1 * distance2));
//
//		int nPointsIn = 0;
//		for (int iPoint = 1; iPoint < nPoints - 1; iPoint++) 
//		{
//			Point2D testPoint = new Point2D.Double((p1.getX() + hx * iPoint), 	(int) (p1.getY() + hy * iPoint));
//
//			if (forma.contains(testPoint) || pointList.size() == 3) 
//			{
//				nPointsIn++;
//			}
//		}
//		if (nPointsIn == 0)
//		{
//			alfa = 2 * Math.PI - alfa;
//		}
////		System.out.println("alfaAf="+alfa*180/Math.PI);
//		return alfa;
//	}
	
	
	public static ArrayList<Point2D> solveArc(Point2D p0, Point2D p1, Point2D p2, double radius)
	{
		ArrayList<Point2D> saida = null;
		
		double distance1, distance2, xa, ya, xb, yb, along;
		Point2D a, b, center;
		
		distance1 = p0.distance(p1);
		distance2 = p0.distance(p2);
		
		double v1v2=(p1.getX() - p0.getX()) * (p2.getX() - p0.getX()) + (p1.getY() - p0.getY()) * (p2.getY() - p0.getY());
		double alfa = Math.acos(v1v2/(distance1 * distance2));
		
		along = radius / Math.tan(alfa / 2); /** <----------- CUIDADO COM O ALFA **/
		
//		System.out.println("********** " + (alfa * 180 / Math.PI));
		
		xa = p0.getX() + along * (p1.getX() - p0.getX()) / distance1;
		ya = (p1.getY() - p0.getY() * (xa - p0.getX())) / (p1.getX() - p0.getX());
		
		xb = p0.getX() + along * (p2.getX() - p0.getX()) / distance2;
		yb = (p2.getY() - p0.getY()) * (xb - p0.getX()) / (p2.getX() - p0.getX()) + p0.getY();
		
		a = new Point2D.Double(xa, ya);
		b = new Point2D.Double(xb, yb);
		
		Point2D c = new Point2D.Double((a.getX() + b.getX()) / 2, (a.getY() + b.getY()) / 2);
		
		double dc = radius / Math.sin(alfa / 2);

		center = pointAlong(p0, c, dc);
//				System.err.println("point a = " + a);
//				System.err.println("point b = " + b);
//				System.err.println("center point = " + center);
		return saida;
	}
	public static ArrayList<Point2D> solveArc(GeneralPath forma, Point2D p0, Point2D p2, Point2D p1, double radius, ArrayList<Point2D> pointList)
	{
		double alfa = solveAngle(p0, p1, p2, forma, pointList);
		//System.out.println("alfa:" + alfa*180/Math.PI);
		
		double h;
		
		Point2D a = new Point2D.Double();
		Point2D b = new Point2D.Double();
		Point2D c = new Point2D.Double();
		Point2D cc = new Point2D.Double();
		
		ArrayList<Point2D> arcPoints = new ArrayList<Point2D>();
		
//		System.out.println("***********************");
//		System.out.println("alfa:" + alfa*180/Math.PI);
//		System.out.println("P0:"+p0.getX()+","+p0.getY());
//		System.out.println("P1:"+p1.getX()+","+p1.getY());
//		System.out.println("P2:"+p2.getX()+","+p2.getY());
		

		double teta = Math.PI - Math.abs(alfa);
		h = radius * Math.cos(teta);
//		System.out.println("teta:" + teta*180/Math.PI);
		double dc = radius / Math.sin(Math.abs(alfa / 2));
		
		if (alfa < 0.0)		
		{
			a = pointT(p0, p2, radius, Math.abs(alfa));
			b = pointT(p0, p1, radius, Math.abs(alfa));
			c.setLocation((a.getX() + b.getX()) / 2, (a.getY() + b.getY()) / 2);
			
//			System.out.println("a:"+a.getX()+","+a.getY());
//			System.out.println("b:"+b.getX()+","+b.getY());			
//			System.out.println("c:"+c.getX()+","+c.getY());
			
			cc = pointAlong(p0, c, dc);
//			System.out.println("CC:"+cc.getX()+","+cc.getY());
			
		}
		else
		{
			a = pointT(p0, p1, radius, alfa);
			b = pointT(p0, p2, radius, alfa);
			c.setLocation((a.getX() + b.getX()) / 2, (a.getY() + b.getY()) / 2);
			
//			System.out.println("a:"+a.getX()+","+a.getY());
//			System.out.println("b:"+b.getX()+","+b.getY());			
//			System.out.println("c:"+c.getX()+","+c.getY());
			
			cc = pointAlong(p0, c, dc);
//			System.out.println("CC:"+cc.getX()+","+cc.getY());
			
		}
		
//		System.out.println("Doing Arc for alpha");
//		System.out.println("a:"+a.getX()+","+a.getY());
//		System.out.println("b:"+b.getX()+","+b.getY());
//		System.out.println("CC:"+cc.getX()+","+cc.getY());
		
		Point2D bb = new Point2D.Double(b.getX()-cc.getX(),b.getY()-cc.getY());
		Point2D aa = new Point2D.Double(a.getX()-cc.getX(),a.getY()-cc.getY());

		//System.out.println("bb:"+bb);
		//System.out.println("aa:"+aa);
		
		//poligonoAuxiliar.add(b);
		//poligonoAuxiliar.add(a);
		
		
		double anguloInicial=Math.atan2(bb.getY(),bb.getX()); 					
		//double anguloFinal=Math.atan2(aa.getY(),aa.getX());
		
		if (anguloInicial<0.0)
		{
			anguloInicial=anguloInicial+2*Math.PI;
		}
		
		double anguloFinal = anguloInicial + teta;
		
		/*
		double deltaAngulo=Math.PI-Math.abs(alfa);
		
		if (anguloFinal<0.0)
		{
			anguloFinal=anguloFinal+2*Math.PI;
		}			
		
		if (anguloFinal<anguloInicial)
		{
			anguloFinal=anguloFinal+2*Math.PI;
		}
		
		*/
		//System.out.println("AInicial: "+anguloInicial*180/Math.PI+" AFinal: "+anguloFinal*180/Math.PI);
		double comprimentoLinha = 1;
		comprimentoLinha=radius/3;
		
		//arcPoints=interpolarArco(cc,radius,anguloInicial, deltaAngulo, comprimentoLinha, true);		
		arcPoints=interpolarArco(cc,radius,anguloInicial, teta, comprimentoLinha, true);
//		System.err.println(arcPoints);
		if (alfa<0.0)
		{
			ArrayList<Point2D> arcPointsOut = new ArrayList<Point2D>();
			for (int i=arcPoints.size()-1;i>=0;i--)
			{
				arcPointsOut.add(arcPoints.get(i));
			}
			return arcPointsOut;
		}
		else
		{
			return arcPoints;	
		}
		//p0 medio
		//p1 derecha
		//p2 izquierda
		
	}	
//	public static ArrayList<Point2D> solveArc(GeneralPath forma, Point2D p0, Point2D p2, Point2D p1, double radius, ArrayList<Point2D> pointList)
//	{
//		double alfa = solveAngle(p0, p1, p2, forma, pointList);
//		double teta = Math.PI / 2 - alfa / 2;
//		double h;
//		ArrayList<Point2D> arcPoints = new ArrayList<Point2D>();
//		/*
//		System.out.println("***********************");
//		System.out.println("alfa:" + alfa * 180 / Math.PI);
//		System.out.println("P0:" + p0.getX() + "," + p0.getY());
//		System.out.println("P1:" + p1.getX() + "," + p1.getY());
//		System.out.println("P2:" + p2.getX() + "," + p2.getY());
//		*/
//
//		if (alfa > Math.PI)		
//		{
//			arcPoints.add(p0);
////			alfa = 2 * Math.PI - alfa;
//		}
//		else
//		{
//			Point2D a = new Point2D.Double();
//			Point2D b = new Point2D.Double();
//			Point2D c = new Point2D.Double();
//			Point2D cc = new Point2D.Double();
//	
//			h = radius * Math.cos(teta);
//	
//			a = pointT(p0, p1, radius, alfa);
//			b = pointT(p0, p2, radius, alfa);
//			c.setLocation((a.getX() + b.getX()) / 2, (a.getY() + b.getY()) / 2);
//	
//			double dc = radius / Math.sin(alfa / 2);
//	
//			cc = pointAlong(p0, c, dc);
//			
////			System.out.println("Doing Arc for alpha");
//			System.out.println("a:"+a.getX()+","+a.getY());
//			System.out.println("b:"+b.getX()+","+b.getY());
//			System.out.println("c:" + c);
//			System.out.println("CC:"+cc.getX()+","+cc.getY());
//			
//			Point2D bb = new Point2D.Double(b.getX() - cc.getX(), b.getY() - cc.getY());
//			Point2D aa = new Point2D.Double(a.getX() - cc.getX(), a.getY() - cc.getY());
//
//			//System.out.println("bb:"+bb);
//			//System.out.println("aa:"+aa);
//			
//			poligonoAuxiliar.add(b);
//			poligonoAuxiliar.add(a);
//			
//			
//			double anguloInicial = Math.atan2(bb.getY(), bb.getX());
//			//double anguloFinal=Math.atan2(aa.getY(),aa.getX());
//			
//			if (anguloInicial < 0.0)
//			{
//				anguloInicial = anguloInicial + 2 * Math.PI;
//			}
//			
//			double anguloFinal = anguloInicial + Math.PI - alfa;
//			
//			double deltaAngulo = Math.PI - alfa;
//			/*
//			if (anguloFinal<0.0)
//			{
//				anguloFinal=anguloFinal+2*Math.PI;
//			}			
//			
//			if (anguloFinal<anguloInicial)
//			{
//				anguloFinal=anguloFinal+2*Math.PI;
//			}
//			*/
//			
//			//System.out.println("AInicial: "+anguloInicial*180/Math.PI+" AFinal: "+anguloFinal*180/Math.PI);
//			double comprimentoLinha = 1;
//			comprimentoLinha = radius / 3;
//			arcPoints=interpolarArco(cc,radius,anguloInicial, deltaAngulo, comprimentoLinha, true);		
//			//p0 medio
//			//p1 derecha
//			//p2 izquierda
//		}
//		return arcPoints;
//	}	
	
	public static Point2D pointT (Point2D p0, Point2D p1, double radius, double alfa)
	{
		Point2D p = new Point2D.Double();
		double d = radius / Math.tan(alfa / 2);
		double x = p0.getX() + (p1.getX() - p0.getX()) / p0.distance(p1) * d;
		double y = p0.getY() + (p1.getY() - p0.getY()) / p0.distance(p1) * d;
		p.setLocation(x, y);
		return p;
	}
	
	public static Point2D pointAlong(Point2D p0, Point2D p1, double d)
	{
		Point2D p = new Point2D.Double();
		double distancep1 = p0.distance(p1);
		int x = (int) (p0.getX() + d * (p1.getX() - p0.getX()) / distancep1);
		int y = (int) (p0.getY() + d * (p1.getY() - p0.getY()) / distancep1);
		p.setLocation(x, y);
		return p;		
	}
	
	public static boolean isCounterClock(ArrayList<Point2D> pontosPoligono)
	{
		boolean isCounterClock = true;
		double area = 0;
		
		for (int i = 0; i < pontosPoligono.size() - 1; i++)
		{
			area += (pontosPoligono.get(i).getX() * pontosPoligono.get(i + 1).getY() - pontosPoligono.get(i + 1).getX() * pontosPoligono.get(i).getY());
		}
		area = area / 2;
//		System.out.println("AREA = " + area);
		
		if(area > 0)
			isCounterClock = true;
		else
			isCounterClock = false;
		
		return isCounterClock;
	}
	
	public static ArrayList<Point2D> transformPolygonInCounterClockPolygon(ArrayList<Point2D> polygon)
	{
		ArrayList<Point2D> novoPoligono = new ArrayList<Point2D>();
		if(!isCounterClock(polygon))
		{
			for(int i = 0; i < polygon.size(); i++)
			{
				novoPoligono.add(new Point2D.Double(polygon.get(polygon.size() - 1 - i).getX(), polygon.get(polygon.size() - 1 - i).getY()));
			}
			return novoPoligono;
		}
		else 
		{
			return polygon;
		}
	}
	
	public static ArrayList<Point2D> interpolarArco(Point2D centro, double raio, double anguloInicial, double deltaAngulo, double comprimentoLinha, boolean isCounterClock)
	{
		ArrayList<Point2D> saida = new ArrayList<Point2D>();
		double incrementoAngulo, x, y;
		int numPontos;
		incrementoAngulo = comprimentoLinha / raio;
		numPontos = (int) Math.ceil(deltaAngulo / incrementoAngulo);
		incrementoAngulo = deltaAngulo / (numPontos - 1);
		
		if (!isCounterClock)
			incrementoAngulo = -incrementoAngulo;
		
		for(int i = 0; i < numPontos; i++)
		{
			x = centro.getX() + raio * Math.cos(anguloInicial + i*incrementoAngulo);
			y = centro.getY() + raio * Math.sin(anguloInicial + i*incrementoAngulo);
			saida.add(new Point2D.Double(x, y));
			
		}
		return saida;
	}
	
	public static ArrayList<Point2D> transformPolygonInRoundPolygon(ArrayList<Point2D> polygon, double radius) 
	{
		ArrayList<Point2D> saida = new ArrayList<Point2D>();
		arcos = new ArrayList<CircularPath>();
		if (radius > 0) 
		{
			double anguloTmp = 0;
			GeneralPath forma = new GeneralPath();
			forma.moveTo(polygon.get(0).getX(), polygon.get(0).getY());
			for (int i = 1; i < polygon.size(); i++) 
			{
				forma.lineTo(polygon.get(i).getX(), polygon.get(i).getY());
			}
			forma.closePath();

			Point2D p0 = null, p1 = null, p2 = null;
			for (int i = 0; i < polygon.size(); i++) 
			{
				p0 = polygon.get(i);
				try 
				{
					p1 = polygon.get(i - 1);
				} catch (Exception e) 
				{
					p1 = polygon.get(polygon.size() - 1);
				}
				try 
				{
					p2 = polygon.get(i + 1);
				} catch (Exception e) 
				{
					p2 = polygon.get(0);
				}
				anguloTmp = solveAngle(p0, p1, p2, forma, polygon);

				if (anguloTmp < 2* Math.PI)
				{
//					System.out.println("angulo = " + (anguloTmp * 180 / Math.PI));
					ArrayList<Point2D> arcoTmp = solveArc(forma, p0, p1, p2, radius, polygon);
					CircularPath arco = new CircularPath(new Point3d(arcoTmp.get(arcoTmp.size() - 1).getX(), arcoTmp.get(arcoTmp.size() - 1).getY(), 0), new Point3d(arcoTmp.get(0).getX(), arcoTmp.get(0).getY(), 0), radius);
					arcos.add(arco);
//					solveArc(p0, p1, p2, radius);
					for (int j = 0; j < arcoTmp.size(); j++) 
					{
						ArrayList<Integer> tempIndex = new ArrayList<Integer>();
						ArrayList<Point2D> tempTriangle = new ArrayList<Point2D>();
						if (j + 1 < arcoTmp.size()) 
						{
							tempTriangle.add(arcoTmp.get(0));
							tempTriangle.add(arcoTmp.get(j));
							tempTriangle.add(arcoTmp.get(j + 1));
							// triangles.add(tempTriangle); ---->>> dar uma
							// olhada
						}
						saida.add(arcoTmp.get(j));
					}
				}
				else 
				{
					saida.add(polygon.get(i));
					poligonoAuxiliar.add(p0); // ----->>> dar uma olhada
				}
			}
		} else 
		{
			saida = polygon;
		}
		// System.out.println("SAIDA: " + saida);
		return saida;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		System.out.println("SSSSSSSSSSSSSS");

		if(e.getKeyCode() == KeyEvent.VK_F9)
		{
			
			System.out.println("SSSSSSSSSSSSSS");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
