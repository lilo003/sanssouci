/*
 * Created by JFormDesigner on Tue Sep 11 15:25:34 BRT 2012
 */

package br.UFSC.GRIMA.cad.visual;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Victor
 */
public class RectangularBossFrame extends JDialog {
	public RectangularBossFrame(Frame owner) {
		super(owner);
		initComponents();
	}

	public RectangularBossFrame(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel3 = new JPanel();
		panel1 = new JPanel();
		toolBar2 = new JToolBar();
		label1 = new JLabel();
		spinnerZoom = new JSpinner();
		panel6 = new JPanel();
		checkBox2 = new JCheckBox();
		spinnerSeparaGrade = new JSpinner();
		toolBar3 = new JToolBar();
		panel2 = new JPanel();
		label2 = new JLabel();
		radiusSpinner = new JSpinner();
		label3 = new JLabel();
		spinnerDepth = new JSpinner();
		label4 = new JLabel();
		spinnerPosZ = new JSpinner();
		panel4 = new JPanel();
		label5 = new JLabel();
		textField1 = new JTextField();
		panel5 = new JPanel();
		label6 = new JLabel();
		spinnerRugosidade = new JSpinner();
		panel7 = new JPanel();
		scrollPane1 = new JScrollPane();
		layeredPane1 = new JLayeredPane();
		xSpinner = new JSpinner();
		ySpinner = new JSpinner();
		lengthSpinner = new JSpinner();
		widthSpinner = new JSpinner();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("Create Rectangular Boss");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setBorder(new EtchedBorder());
				contentPanel.setLayout(new BorderLayout());

				//======== panel3 ========
				{
					panel3.setLayout(new GridBagLayout());
					((GridBagLayout)panel3.getLayout()).columnWidths = new int[] {0, 0, 0};
					((GridBagLayout)panel3.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
					((GridBagLayout)panel3.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
					((GridBagLayout)panel3.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

					//======== panel1 ========
					{
						panel1.setLayout(new FlowLayout(FlowLayout.LEFT));

						//======== toolBar2 ========
						{

							//---- label1 ----
							label1.setText("zoom");
							toolBar2.add(label1);

							//---- spinnerZoom ----
							spinnerZoom.setModel(new SpinnerNumberModel(100.0, 0.0, 500.0, 10.0));
							toolBar2.add(spinnerZoom);

							//======== panel6 ========
							{
								panel6.setBorder(new EtchedBorder());
								panel6.setLayout(new GridBagLayout());
								((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {0, 40, 0};
								((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0};
								((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
								((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

								//---- checkBox2 ----
								checkBox2.setText("grid");
								checkBox2.setSelected(true);
								panel6.add(checkBox2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 5), 0, 0));

								//---- spinnerSeparaGrade ----
								spinnerSeparaGrade.setModel(new SpinnerNumberModel(20.0, 1.0, null, 5.0));
								panel6.add(spinnerSeparaGrade, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							toolBar2.add(panel6);
						}
						panel1.add(toolBar2);

						//======== toolBar3 ========
						{

							//======== panel2 ========
							{
								panel2.setBorder(new EtchedBorder());
								panel2.setLayout(new GridBagLayout());
								((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 45, 0, 0, 45, 45, 0, 40, 0};
								((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
								((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
								((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

								//---- label2 ----
								label2.setText("radius");
								panel2.add(label2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 5), 0, 0));

								//---- radiusSpinner ----
								radiusSpinner.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
								radiusSpinner.setEnabled(false);
								panel2.add(radiusSpinner, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 5), 0, 0));

								//---- label3 ----
								label3.setText("height");
								panel2.add(label3, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 5), 0, 0));

								//---- spinnerDepth ----
								spinnerDepth.setModel(new SpinnerNumberModel(10.0, 0.0, null, 1.0));
								panel2.add(spinnerDepth, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 5), 0, 0));

								//---- label4 ----
								label4.setText("pos Z");
								panel2.add(label4, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 5), 0, 0));

								//---- spinnerPosZ ----
								spinnerPosZ.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
								panel2.add(spinnerPosZ, new GridBagConstraints(7, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.CENTER, GridBagConstraints.BOTH,
									new Insets(0, 0, 0, 0), 0, 0));
							}
							toolBar3.add(panel2);
						}
						panel1.add(toolBar3);
					}
					panel3.add(panel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));

					//======== panel4 ========
					{
						panel4.setLayout(new GridBagLayout());
						((GridBagLayout)panel4.getLayout()).columnWidths = new int[] {0, 155, 0, 0, 0, 0, 0, 0};
						((GridBagLayout)panel4.getLayout()).rowHeights = new int[] {0, 0};
						((GridBagLayout)panel4.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
						((GridBagLayout)panel4.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

						//---- label5 ----
						label5.setText("name");
						panel4.add(label5, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- textField1 ----
						textField1.setText("RECTANGULAR BOSS");
						panel4.add(textField1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//======== panel5 ========
						{
							panel5.setLayout(new FlowLayout());

							//---- label6 ----
							label6.setText("Roughness");
							panel5.add(label6);
						}
						panel4.add(panel5, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));

						//---- spinnerRugosidade ----
						spinnerRugosidade.setModel(new SpinnerNumberModel(50.0, 0.0, 50.0, 1.0));
						panel4.add(spinnerRugosidade, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.BOTH,
							new Insets(0, 0, 0, 5), 0, 0));
					}
					panel3.add(panel4, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
				}
				contentPanel.add(panel3, BorderLayout.NORTH);

				//======== panel7 ========
				{
					panel7.setLayout(new GridBagLayout());
					((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {0, 505, 0, 0};
					((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 305, 0, 0};
					((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
					((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};

					//======== scrollPane1 ========
					{

						//======== layeredPane1 ========
						{

							//---- xSpinner ----
							xSpinner.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
							xSpinner.setVisible(false);
							layeredPane1.add(xSpinner, JLayeredPane.DEFAULT_LAYER);
							xSpinner.setBounds(0, 0, 41, 20);

							//---- ySpinner ----
							ySpinner.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
							ySpinner.setVisible(false);
							layeredPane1.add(ySpinner, JLayeredPane.DEFAULT_LAYER);
							ySpinner.setBounds(115, 15, 31, 20);

							//---- lengthSpinner ----
							lengthSpinner.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
							lengthSpinner.setVisible(false);
							layeredPane1.add(lengthSpinner, JLayeredPane.DEFAULT_LAYER);
							lengthSpinner.setBounds(195, 65, 45, lengthSpinner.getPreferredSize().height);

							//---- widthSpinner ----
							widthSpinner.setModel(new SpinnerNumberModel(0.0, 0.0, null, 1.0));
							widthSpinner.setVisible(false);
							layeredPane1.add(widthSpinner, JLayeredPane.DEFAULT_LAYER);
							widthSpinner.setBounds(285, 70, 40, widthSpinner.getPreferredSize().height);
						}
						scrollPane1.setViewportView(layeredPane1);
					}
					panel7.add(scrollPane1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 5, 5), 0, 0));
				}
				contentPanel.add(panel7, BorderLayout.CENTER);
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

				//---- okButton ----
				okButton.setText("OK");
				buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- cancelButton ----
				cancelButton.setText("Cancel");
				buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	protected JPanel contentPanel;
	private JPanel panel3;
	private JPanel panel1;
	private JToolBar toolBar2;
	private JLabel label1;
	protected JSpinner spinnerZoom;
	private JPanel panel6;
	protected JCheckBox checkBox2;
	protected JSpinner spinnerSeparaGrade;
	private JToolBar toolBar3;
	private JPanel panel2;
	private JLabel label2;
	public JSpinner radiusSpinner;
	private JLabel label3;
	protected JSpinner spinnerDepth;
	private JLabel label4;
	protected JSpinner spinnerPosZ;
	private JPanel panel4;
	private JLabel label5;
	protected JTextField textField1;
	private JPanel panel5;
	private JLabel label6;
	protected JSpinner spinnerRugosidade;
	private JPanel panel7;
	private JScrollPane scrollPane1;
	protected JLayeredPane layeredPane1;
	public JSpinner xSpinner;
	public JSpinner ySpinner;
	public JSpinner lengthSpinner;
	public JSpinner widthSpinner;
	private JPanel buttonBar;
	protected JButton okButton;
	protected JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
