package br.UFSC.GRIMA.shopFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import br.UFSC.GRIMA.entidades.machiningResources.DrillingMachine;
import br.UFSC.GRIMA.entidades.machiningResources.MachineTool;
import br.UFSC.GRIMA.entidades.machiningResources.MillingMachine;
import br.UFSC.GRIMA.entidades.machiningResources.MillingTypeSpindle;
import br.UFSC.GRIMA.entidades.machiningResources.Spindle;
import br.UFSC.GRIMA.entidades.machiningResources.TurningTypeSpindle;
import br.UFSC.GRIMA.shopFloor.visual.CreateSpindleFrame;
/**
 * 
 * @author jc
 *
 */
public class CreateSpindle extends CreateSpindleFrame implements ActionListener, ItemListener 
{
	private CreateMillingMachine janelaMillingMachine;
	private CreateDrillingMachine janelaDrillingMachine;
	private MachineTool millingMachine;
	private Spindle spindle;
	private String name;
	private boolean isCoolant;
	private double itsSpeedRange;
	private double maxTorque;
	private double spindleMaxPower;
	private double maxDiameter;
	private String type;
	private ArrayList<Spindle> arraySpindle;
	public PowerTorquePanel powerTorquePanel;
	
	public CreateSpindle(CreateDrillingMachine janelaDrillingMachine, MachineTool drillingMachine)
	{
		super(janelaDrillingMachine);
		this.millingMachine = drillingMachine;
		this.janelaDrillingMachine = janelaDrillingMachine;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.comboBox1.addItemListener(this);
		this.powerTorquePanel = new PowerTorquePanel(this);
		this.scrollPane1.setViewportView(this.powerTorquePanel);
		this.powerTorquePanel.revalidate();
		this.scrollPane1.revalidate();
		this.spinner5.addChangeListener(new ChangeListener() 
		{
					@Override
			public void stateChanged(ChangeEvent arg0) 
			{
				powerTorquePanel.repaint();
			}
		});
		this.spinner6.addChangeListener(new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				powerTorquePanel.repaint();
			}
		});
		this.spinner7.addChangeListener(new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				powerTorquePanel.repaint();
			}
		});
	}
	public CreateSpindle(CreateMillingMachine janelaMillingMachine, MachineTool millingMachine)
	{
		super(janelaMillingMachine);
		this.millingMachine = millingMachine;
		this.janelaMillingMachine = janelaMillingMachine;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.comboBox1.addItemListener(this);
		this.powerTorquePanel = new PowerTorquePanel(this);
		this.scrollPane1.setViewportView(this.powerTorquePanel);
		this.powerTorquePanel.revalidate();
		this.scrollPane1.revalidate();
		this.spinner5.addChangeListener(new ChangeListener() 
		{
					@Override
			public void stateChanged(ChangeEvent arg0) 
			{
				powerTorquePanel.repaint();
			}
		});
		this.spinner6.addChangeListener(new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				powerTorquePanel.repaint();
			}
		});
		this.spinner7.addChangeListener(new ChangeListener() 
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				powerTorquePanel.repaint();
			}
		});
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		int index = this.comboBox1.getSelectedIndex();
		
		if(e.equals(comboBox1)){
			
			switch (index) {
			case 0:		
				this.label1.setText("Max cutting tool diameter");
				break;
			case 1:
				this.label1.setText("Max workpiece diameter");		
				break;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		 Object o = e.getSource();
		 
		 if(o.equals(okButton))
		 {
			 this.ok();
			 this.dispose();			 
		 }
		 else if(o.equals(cancelButton))
		 {
			 this.dispose();
		 }
	}

	private void ok()
	{
		name = this.textField1.getText();
		isCoolant = this.checkBox1.isSelected();
		itsSpeedRange = ((Double) this.spinner5.getValue()).doubleValue();
		maxTorque = ((Double) this.spinner7.getValue()).doubleValue();
		spindleMaxPower = ((Double) this.spinner6.getValue()).doubleValue();
		maxDiameter = ((Double) this.spinner1.getValue()).doubleValue();
		arraySpindle = millingMachine.getItsSpindle();
	
		
		if(this.comboBox1.getSelectedIndex() == 0)
		{
			spindle = new MillingTypeSpindle();
			type = "Milling";
			((MillingTypeSpindle) spindle).setMaxCuttingToolDiameter(maxDiameter);
		}
		else
		{
			spindle = new TurningTypeSpindle();
			type = "Turning";
			((TurningTypeSpindle) spindle).setMaxWorkpieceDiameter(maxDiameter);
		}
		spindle.setItsId(name);
		spindle.setIsCoolant(isCoolant);
		spindle.setItsSpeedRange(itsSpeedRange);
		spindle.setMaxTorque(maxTorque);
		spindle.setSpindleMaxPower(spindleMaxPower);
		
		Object[] linha = {false, name, type, maxDiameter, spindleMaxPower, maxTorque, itsSpeedRange, isCoolant};
		if(this.janelaMillingMachine != null){
			DefaultTableModel modelo = (DefaultTableModel)this.janelaMillingMachine.table4.getModel();
		
		this.janelaMillingMachine.table4.setModel(modelo);
		modelo.addRow(linha);
		this.janelaMillingMachine.scrollPane5.setViewportView(this.powerTorquePanel);
		this.powerTorquePanel.revalidate();
		this.janelaMillingMachine.scrollPane5.revalidate();
		}else
		{
			DefaultTableModel modelo = (DefaultTableModel)this.janelaDrillingMachine.table4.getModel();
			
			this.janelaDrillingMachine.table4.setModel(modelo);
			modelo.addRow(linha);
			this.janelaDrillingMachine.scrollPane5.setViewportView(this.powerTorquePanel);
			this.powerTorquePanel.revalidate();
			this.janelaDrillingMachine.scrollPane5.revalidate();
		}
		arraySpindle.add(spindle);
		millingMachine.setItsSpindle(arraySpindle);
	}
}
