import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;

import javax.swing.*;
/*
public class GifManipulation extends JPanel{
	private List<BufferedImage> imgList = new ArrayList<BufferedImage>();
	private int delayTime = 0;
	private GamePanel gp;
	private Timer timer;
	private int curImg;
	 

	public GifManipulation(String filename,GamePanel parent) {
		this.gp = parent;
		curImg = 0;
	    try {
	        ImageReader reader = ImageIO.getImageReadersBySuffix("gif").next();
	        reader.setInput(ImageIO.createImageInputStream(new FileInputStream(filename)));
	        int i = reader.getMinIndex();
	        int numImages = reader.getNumImages(true);
	        while (i < numImages)
	        {
	        	setOpaque(false);
	        	imgList.add(reader.read(i++));
	        }
	        IIOMetadata imageMetaData =  reader.getImageMetadata(0);
	        String metaFormatName = imageMetaData.getNativeMetadataFormatName();

	        IIOMetadataNode root = (IIOMetadataNode)imageMetaData.getAsTree(metaFormatName);

	        IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");
	        delayTime = Integer.valueOf(graphicsControlExtensionNode.getAttribute("delayTime").toString());
	        System.out.println(graphicsControlExtensionNode.getAttribute("delayTime"));
	        
	        timer = new Timer(delayTime*10, (ActionEvent e) -> {
	            repaint();
	        });
	    
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
    private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName){
        int nNodes = rootNode.getLength();
        for (int i = 0; i < nNodes; i++) {
            if (rootNode.item(i).getNodeName().compareToIgnoreCase(nodeName)== 0) {
            return((IIOMetadataNode) rootNode.item(i));
            }
       }
        IIOMetadataNode node = new IIOMetadataNode(nodeName);
        rootNode.appendChild(node);
        return(node);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgList.get(curImg % imgList.size()), 0, 0,null);
        curImg++;
    }
    
    
    public void printGif(Graphics g, int x,int y) throws InterruptedException {
    	setLocation(x, y);
    	timer.start();
    }
	
}
*/