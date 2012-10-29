package br.UFSC.GRIMA.cad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.vecmath.Point3d;

import br.UFSC.GRIMA.cad.bezierGraphicInterface.ColorComboBox;
import br.UFSC.GRIMA.cad.visual.CreateRegionFrame;
import br.UFSC.GRIMA.entidades.features.Region;

public class CriarRegionFrame extends CreateRegionFrame implements ChangeListener, ItemListener, ActionListener, WindowListener{

		private BezierSurfacePanel beziersurfacepanel;
		private Region region;
		public static JComboBox choice;
		static ColorComboBox color;
		static JCheckBox checkbox;
		static JSpinner n;
		private Point3d[][] vertex;
		int[] OVER = { -1, -1 };
		
		JButton reset;
		JButton zoomin;
		JButton zoomout;
		private double[][][] control_vertex = new double[4][4][3];
		
//		SplashPanel splash;

		
		public CriarRegionFrame (JanelaPrincipal parent)
		{
			super(parent);
			this.setSize(600, 400);
//			beziersurfacepanel = new BezierSurfacePanel();
//			this.getContentPane().add(beziersurfacepanel, BorderLayout.CENTER);
//			this.setVisible(true);
			this.okButton.addActionListener(this);
			this.cancelButton.addActionListener(this);
			this.init();
		}
		public CriarRegionFrame (JanelaPrincipal parent, Region region)
		{
			super(parent);
			this.region = region;
			this.setSize(600, 400);
			this.okButton.addActionListener(this);
			this.cancelButton.addActionListener(this);
			Point3d [][] points = region.getControlVertex();
			double escala = (points[3][3].x - points[0][0].x) / 5;
			for(int i = 0; i < points.length; i++)
			{
				for(int j = 0; j < points[i].length; j++)
				{
//					System.out.println("****** POINTS: " + points[i][j]);
					control_vertex[i][j][0] = (points[i][j].x - (points[3][3].x - points[0][0].x) / 2) / escala;
					control_vertex[i][j][1] = (points[i][j].y - (points[3][3].y - points[0][0].y) / 2) / escala;
					control_vertex[i][j][2] = points[i][j].z;
				}
			}
			this.init();
		}
		
		public void init(){
			setBackground(Color.white);
//			Container contentpane = this.getContentPane();
//			contentpane.setLayout( new BorderLayout() );
			
			JPanel north = new JPanel( new BorderLayout() );
			JPanel northwest = new JPanel();
			choice = new JComboBox();
				northwest.add( choice );
					choice.addItem("Wire Frame - Transparent");
					choice.addItem("Wire Frame - Opaque");
					choice.addItem("Transparent");
					choice.addItem("Opaque");
					choice.addItem("u,v-parametrization");
				northwest.add( color = new ColorComboBox() );
				northwest.add( checkbox = new JCheckBox( "Control Points", true ) );
				
//				choice.setVisible(false); //deixa invisivel no frame a opcao de escolher o choice
				
			north.add( "West", northwest );
				
				JPanel northeast = new JPanel();
					northeast.add( new JLabel( " n=" ) );
					northeast.add( n = new JSpinner( new SpinnerNumberModel(30,1,100,1) ) );
//			north.add( "East", northeast );
			north.setBorder( BorderFactory.createTitledBorder( north.getBorder(), "Controls", TitledBorder.LEFT, TitledBorder.TOP, new Font( "Helvetica", Font.BOLD, 15) ) );
			
				
			JPanel south = new JPanel( new BorderLayout() );
				JPanel southwest = new JPanel();
					southwest.add( reset = new JButton( "Reset" ) );
				south.add( "West", southwest );
				JPanel southeast = new JPanel();
					southeast.add( zoomin = new JButton("Zoom In") );
					southeast.add( zoomout = new JButton("Zoom Out") );
				south.add( "East", southeast );
				JLabel statusbar = new JLabel("");
					statusbar.setPreferredSize( new Dimension(15,15) );
				south.add( "South", statusbar );
			south.setBorder( BorderFactory.createTitledBorder( south.getBorder(), "", TitledBorder.LEFT, TitledBorder.TOP, new Font( "Helvetica", Font.BOLD, 15) ) );

//			contentpane.add( "North", north );
			panel2.add( "South", south );
//			contentpane.add( "Center", beziersurfacepanel = new BezierSurfacePanel() );
			
			panel2.add( "Center", beziersurfacepanel = new BezierSurfacePanel(control_vertex) );
			
			reset.addActionListener( this );
			zoomin.addActionListener( this );
			zoomout.addActionListener( this );
			checkbox.addActionListener( this );
			n.addChangeListener( this );
			choice.addItemListener( this );
			color.addItemListener( this );
			this.addWindowListener( this );
						
//			splash = new SplashPanel( this );
//			getContentPane().add( splash );
		
				
		}
		
		public void showFrame(){
			this.setVisible( true );
		}


//		public void start(){
//			//splash.active = true;
//			if ( !frame.isVisible() ) splash.start();
//		}

//		public void stop(){
//			splash.active = false;
//			//frame.setVisible( false );
//		}

	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();

		if (obj == zoomin) {
			beziersurfacepanel.zoomIn();
			if (beziersurfacepanel.zoom == 0)
				zoomin.setEnabled(false);
			zoomout.setEnabled(true);
		} else if (obj == zoomout) {
			beziersurfacepanel.zoomOut();
			if (beziersurfacepanel.zoom == beziersurfacepanel.units.length - 1)
				zoomout.setEnabled(false);
			zoomin.setEnabled(true);
		} else if (obj.equals(reset)) {
			beziersurfacepanel.reset();
		} else if (obj.equals(checkbox)) {
			beziersurfacepanel.repaint();
		} else if (obj.equals(cancelButton)) {
			dispose();
		} else if (obj.equals(okButton)) {
			this.ok();
		}
	}
	/**
	 *  	Acoes que acontecerao quando o usuario clicar no botao OK
	 */
	private void ok() 
	{
		vertex = new Point3d[4][4];
		for(int i = 0; i < beziersurfacepanel.control_points.length; i++)
		{
			for(int j = 0; j < beziersurfacepanel.control_points[i].length; j++)
			{
				double x = beziersurfacepanel.control_points[i][j][0] * 5 * 8;
				double y = beziersurfacepanel.control_points[i][j][1] * 5 * 8;
				double z = beziersurfacepanel.control_points[i][j][2] * 50;
				
				vertex[i][j] = new Point3d(x, y, z);
				System.out.println("VERTEX: " + new Point3d(x, y, z));
			}
		}
		region.setControlVertex(vertex);
	}

		public void itemStateChanged( ItemEvent ie ){
				Object obj = ie.getSource();
				if ( obj == choice ){
					beziersurfacepanel.newCoef = true;
					beziersurfacepanel.repaint();
				} else if ( obj == color ){
					beziersurfacepanel.repaint();
				}
			}
		 
		 public void stateChanged( ChangeEvent ce ){
				Object obj = ce.getSource();
				if ( obj == n ){
					beziersurfacepanel.N = ((Integer)n.getValue()).intValue();
					beziersurfacepanel.newCoef = true;
					beziersurfacepanel.repaint();
				}
			}
		 

			public void windowActivated( WindowEvent we ){
			}

			public void windowClosed( WindowEvent we ){
				//splash.start();
			}

//			public void windowClosing( WindowEvent we ){
//				splash.start();
//			}

			public void windowDeactivated( WindowEvent we ){
				//splash.start();
			}

			public void windowDeiconified( WindowEvent we ){
			}

			public void windowIconified( WindowEvent we ){
			}

			public void windowOpened( WindowEvent we ){
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
}


		