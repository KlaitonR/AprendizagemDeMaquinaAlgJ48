package principal;

import java.io.File;
import java.text.DecimalFormat;
import algoritmos.AprendizagemBayesiana;
import extrator_caracteristicas.ExtractCaracteristicas;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class PrincipalController {
	
	@FXML private ImageView imageView;
	
	@FXML Label marronEdna;
	@FXML Label verdeEdna;
	@FXML Label azulEdna;
	@FXML Label azulMilhouse;
	@FXML Label violetaMilhouse;
	@FXML Label vermelhoMilhouse;
	@FXML Label classe;
	@FXML Label prClasNavieBayes;
	@FXML Label prClasJ48;
	
	private double [] c = {0,0,0,0,0,0};
	
	private DecimalFormat df = new DecimalFormat("##0.0000");
	
	@FXML
	public void extrairCaracteristicas() {
		ExtractCaracteristicas.extrair();
	}
	
	@FXML
	public void selecionaImagem() {
		File f = buscaImg();
		if(f != null) {
			Image img = new Image(f.toURI().toString());
			imageView.setImage(img);
			imageView.setFitWidth(img.getWidth());
			imageView.setFitHeight(img.getHeight());
			double[] caracteristicas = ExtractCaracteristicas.extraiCaracteristicas(f);

			marronEdna.setText(String.valueOf(df.format(caracteristicas[0])));
			verdeEdna.setText(String.valueOf(df.format(caracteristicas[1])));
			azulEdna.setText(String.valueOf(df.format(caracteristicas[2])));
			azulMilhouse.setText(String.valueOf(df.format(caracteristicas[3])));
			violetaMilhouse.setText(String.valueOf(df.format(caracteristicas[4])));
			vermelhoMilhouse.setText(String.valueOf(df.format(caracteristicas[5])));
			
			if(caracteristicas[6] == 0) 
				classe.setText("Edna");
			else if (caracteristicas[6] == 1)
				classe.setText("Milhouse");
			else 
				classe.setText("Desconhecida");
			
			c = caracteristicas;
		}
	}
	
	@FXML 
	public void navieBayes() {
		
		double[] nb = AprendizagemBayesiana.naiveBayes(c);
		prClasNavieBayes.setText("Probabilidade de ser a Edna: " + df.format(nb[0]*100)+ "% \n" +
				"Probabilidade de ser o Milhouse: " + df.format(nb[1]*100)+ "%");
	}
	
	@FXML 
	public void j48() {
		
		double[] j48 = algoritmos.J48.j48(c);
		prClasJ48.setText("Probabilidade de ser a Edna: " + df.format(j48[0]*100)+ "% \n" +
				"Probabilidade de ser o Milhouse: " + df.format(j48[1]*100)+ "%");
	}
	
	private File buscaImg() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new 
				   FileChooser.ExtensionFilter(
						   "Imagens", "*.jpg", "*.JPG", 
						   "*.png", "*.PNG", "*.gif", "*.GIF", 
						   "*.bmp", "*.BMP")); 	
		 fileChooser.setInitialDirectory(new File("src/imagens"));
		 File imgSelec = fileChooser.showOpenDialog(null);
		 try {
			 if (imgSelec != null) {
			    return imgSelec;
			 }
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		 return null;
	}
	
}
