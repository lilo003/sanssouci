package br.UFSC.GRIMA.shopFloor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JDialog;
import javax.swing.table.DefaultTableModel;

import br.UFSC.GRIMA.cad.visual.AddNewBoringToolFrame;
import br.UFSC.GRIMA.entidades.ferramentas.BoringTool;
import br.UFSC.GRIMA.entidades.ferramentas.Ferramenta;
import br.UFSC.GRIMA.entidades.machiningResources.CuttingToolHandlingDevice;

public class AddNewBoringTool extends AddNewBoringToolFrame implements ActionListener, ItemListener
{
	private String materialClass = "P";
	private int handOfCut = 1;
	private CreateMillingMachine janelaMillingMachine;
	private CreateDrillingMachine janelaDrillingMachine;
	public AddNewBoringTool(CreateMillingMachine owner) 
	{
		super(owner);
		this.janelaMillingMachine = (CreateMillingMachine) owner;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.comboBox1.addItemListener(this);
		this.comboBox3.addItemListener(this);
	}
	
	public AddNewBoringTool(CreateDrillingMachine owner) 
	{
		super(owner);
		this.janelaDrillingMachine =  owner;
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);
		this.comboBox1.addItemListener(this);
		this.comboBox3.addItemListener(this);
	}
	@Override
	public void itemStateChanged(ItemEvent e) 
	{		
		Object o = e.getItem();
		if(o == this.comboBox1.getItemAt(0))
		{
			this.materialClass = "P";
		} else if (o == this.comboBox1.getItemAt(1))
		{
			this.materialClass = "M";
		} else if (o == this.comboBox1.getItemAt(2))
		{
			this.materialClass = "S";
		} else if (o == this.comboBox1.getItemAt(3))
		{
			this.materialClass = "K";
		} else if (o == this.comboBox1.getItemAt(4))
		{
			this.materialClass = "H";
		} else if (o == this.comboBox1.getItemAt(5))
		{
			this.materialClass = "N";
		} else if (o == this.comboBox3.getItemAt(0))
		{
			this.handOfCut = Ferramenta.RIGHT_HAND_OF_CUT;
		}  else if (o == this.comboBox3.getItemAt(1))
		{
			this.handOfCut = Ferramenta.LEFT_HAND_OF_CUT;
		}  else if (o == this.comboBox3.getItemAt(2))
		{
			this.handOfCut = Ferramenta.NEUTRAL_HAND_OF_CUT;
		} 
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object object = e.getSource();
		if(object == okButton)
		{
			this.ok();
		} else if (object == cancelButton)
		{
			this.dispose();
		}
	}
	private void ok()
	{
		
		String nome = this.textField1.getText();
		double diametro = (Double)this.spinner4.getValue();
		double dMinimo = (Double)this.spinner6.getValue();
		double cuttingEdge = (Double)this.spinner5.getValue();
		double profundidade = (Double)this.spinner2.getValue();
		double offSetLength = (Double)this.spinner1.getValue();
		double dm = (Double)this.spinner3.getValue();
		double edgeRadius = 0; // ==========??????????
		String material = "Carbide";
		double rugosidade = 0;
		double tolerancia = 0;
		String hand = "";
		if (handOfCut == Ferramenta.RIGHT_HAND_OF_CUT)
			hand = "Right";
		else if(this.handOfCut == Ferramenta.LEFT_HAND_OF_CUT)
			hand = "Left";
		else if(this.handOfCut == Ferramenta.NEUTRAL_HAND_OF_CUT)
			hand = "Neutral";
		if(janelaMillingMachine !=(null)){
		int id = this.janelaMillingMachine.table1.getRowCount() + 1;
		BoringTool bt = new BoringTool(nome, material, diametro, dMinimo, edgeRadius, cuttingEdge, profundidade, offSetLength, dm, rugosidade, tolerancia, handOfCut);
		janelaMillingMachine.toolList.add(bt);
		Object[] linha = {false, id, nome, diametro, "Boring Tool"};
		DefaultTableModel modelo = (DefaultTableModel)this.janelaMillingMachine.table1.getModel();
		this.janelaMillingMachine.table1.setModel(modelo);
		modelo.addRow(linha);
		}else if(janelaDrillingMachine != null){
			int id = this.janelaDrillingMachine.table1.getRowCount() + 1;
			BoringTool bt = new BoringTool(nome, material, diametro, dMinimo, edgeRadius, cuttingEdge, profundidade, offSetLength, dm, rugosidade, tolerancia, handOfCut);
			janelaDrillingMachine.toolList.add(bt);
			Object[] linha = {false, id, nome, diametro, "Boring Tool"};
			DefaultTableModel modelo = (DefaultTableModel)this.janelaDrillingMachine.table1.getModel();
			this.janelaDrillingMachine.table1.setModel(modelo);
			modelo.addRow(linha);
			}/*else if(janelaTurningMachine != (null)){
				int id = this.janelaTurningMachine.table1.getRowCount() + 1;
				BoringTool bt = new BoringTool(nome, material, diametro, dMinimo, edgeRadius, cuttingEdge, profundidade, offSetLength, dm, rugosidade, tolerancia, handOfCut);
				janelaTurningMachine.toolList.add(bt);
				Object[] linha = {false, id, nome, diametro, "Boring Tool"};
				DefaultTableModel modelo = (DefaultTableModel)this.janelaTurningMachine.table1.getModel();
				this.janelaTurningMachine.table1.setModel(modelo);
				modelo.addRow(linha);
				}
				*/
		this.dispose();
	}
}
