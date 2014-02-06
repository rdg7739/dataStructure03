package proj3;
import java.util.*;
import  java.io.*;
public class Proj3
{

	private ArrayList<HuffmanTree<Character>> forest;
	private HuffmanTree<Character> tree;
	private Map<Character, String> map = new HashMap<Character, String>();
	private FileInputStream in = null;
	private FileOutputStream out = null;
	/**
	 * constructor read file and ask to do compress and uncompress
	 * @param args - get the texts' title
	 * @throws IOException
	 */
	public Proj3(String args[]) throws IOException{
		String inputFileName = args[0], 
				compressedOutputFileName = args[1], 
				uncompressedOutputFileName = args[2];
		
		
		System.out.println("Compressing: " + inputFileName + " into " + compressedOutputFileName);
		System.out.println("Uncompressing: " + compressedOutputFileName + " into " + uncompressedOutputFileName);
		
		try {
			in = new FileInputStream(inputFileName);				//for compress 
			out = new FileOutputStream(compressedOutputFileName);
			tree = margeToOne(makeForest(inputFileName));			//make a forest and marge it to one tree
			if(tree == null){
				mappingCompress(null, map);
				compress(in, out, map);
			}
			else{
				tree.setKey(tree.getRoot());							//set the key at map
				mappingCompress(tree.getRoot(), map);
				compress(in, out, map);
			}
			in.close();
			out.close();
			in = new FileInputStream(compressedOutputFileName);		 //for uncompress
			out = new FileOutputStream(uncompressedOutputFileName);
			if(tree == null)
				uncompress(in, out, null);
			else
				uncompress(in, out, tree.getRoot());
			
			System.out.println("Character\t\tFrequency");
			System.out.println("-------------------------------");
			tree.printInOrderFreq();
			System.out.println("Total Characters: " + tree.size());
			System.out.println("-------------------------------");
			System.out.println("Character\t\tFrequency");
			System.out.println("-------------------------------");
			tree.printInOrderMap(map);
			System.out.println("Total Characters: " + tree.size());
			System.out.println("-------------------------------");
			

		}catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}catch (IOException e) {
			System.out.println(e.toString());
		}finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}

	}
	/**
	 * set the key and value of nodes
	 * @param node of character
	 * @param map - character as key and string as value
	 */
	private void mappingCompress(HuffmanNode<Character> node, Map<Character, String> map){
		if(node != null){
			if(node.getLeft() == null && node.getRight() == null){
				map.put((Character) node.getElement(), node.getKey());
			}
			if(node.getLeft() != null){
				mappingCompress(node.getLeft(), map);
			}

			if(node.getRight() != null){
				mappingCompress(node.getRight(), map);
			}
		}
	}
	/**
	 * compress regular text file
	 * @param in - text file's name
	 * @param out - compressed file's name
	 * @param mapCompress - has the binary code of each character
	 * @throws IOException
	 */
	private void compress(FileInputStream in, FileOutputStream out, Map<Character, String> mapCompress) throws IOException{
		StringBuffer foo = new StringBuffer();
		int c;
		boolean isEmpty = true;
		while((c = in.read()) != -1){
			isEmpty = false;
			foo.append(mapCompress.get((char)c));
			if(foo.length() > 8){
				int hoo = Integer.parseInt(foo.substring(0, 8),2);
				out.write(hoo);
				foo.delete(0, 8);
			}
		}
		if(!isEmpty){
		int hoo = Integer.parseInt(foo.substring(0, foo.length()),2);
		out.write(hoo);
		foo.delete(0, foo.length());
		}
	}
	/**
	 * uncompress the compress file
	 * @param in - compress file's file name
	 * @param out - uncompress file's file name
	 * @param root - root of tree
	 * @throws IOException
	 */
	private void uncompress(FileInputStream in, FileOutputStream out, HuffmanNode<Character> root) throws IOException{
		Character result;
		int c;
		int i = 1;
		StringBuffer text = new StringBuffer();
		while ((c = in.read()) != -1)
		{	
			StringBuffer read = new StringBuffer();
			read.append(Integer.toBinaryString(c));
			if(in.available() != 0){
				while(read.length() < 8){
					read.insert(0, 0);
				}
				i++;
				text.append(read.substring(0));
			}
			else{
				text.append(Integer.toBinaryString(c));
			}

		}
		while(text.length() > 0){
			result = getChar(text, root);
			if(root.getLeft() == null && root.getRight() == null){
				text.deleteCharAt(0);
			}
			if(result == null){
			}
			else{
				out.write(result);
			}

		}
	}
	/**
	 * get character from string buffer
	 * @param in - the binary 
	 * @param node
	 * @return
	 */
	private Character getChar(StringBuffer in, HuffmanNode<Character> node){
		Character result;
		if(in.length() <= 0 || in == null){
			if(node.getLeft() == null && node.getRight() == null){
				return node.getElement();
			}
			else{
				return null;
			}
		}		
		Character foo = in.charAt(0);
		if(node.getLeft() == null && node.getRight() == null){
			return node.getElement();
		}
		if(foo.equals('0')){
			in.deleteCharAt(0);
			result = getChar(in, node.getLeft());
		}
		else{
			in.deleteCharAt(0);
			result = getChar(in, node.getRight());
		}
		return result;

	}
	/**
	 * get trees from list and make it to one tree 
	 * @param forest - list of tree
	 * @return - one tree that combined whole trees
	 */
	private HuffmanTree<Character> margeToOne(ArrayList<HuffmanTree<Character>> forest){
		HuffmanTree<Character> small1, small2, instant = null;
		if(forest.isEmpty()){
			return null;
		}
		if(forest.size() == 1){
			return forest.get(0);
		}
		while(forest.size() > 1){
			int small1_freq = forest.get(0).getRoot().getFrequency();
			small1 = forest.get(0);
			for(int i = 0; i < forest.size()-1; i++){			 
				instant = forest.get(i);
				if(small1_freq > instant.getRoot().getFrequency()){
					small1 = instant;
					small1_freq = small1.getRoot().getFrequency();
				}
			}
			forest.remove(small1);
			int small2_freq = forest.get(0).getRoot().getFrequency();
			small2 = forest.get(0);
			for(int i = 0; i < forest.size()-1; i++){			 
				instant = forest.get(i);
				if(small2_freq > instant.getRoot().getFrequency()){
					small2 = instant;
					small2_freq = small2.getRoot().getFrequency();
				}
			}
			forest.remove(small2);
			HuffmanTree<Character> marged = new HuffmanTree<Character>();
			marged.merge(null, small1, small2, small2_freq + small1_freq);
			forest.add(marged);
		}
		return forest.get(0);
	}
	/**
	 * make a tree with every character and store it at arraylist
	 * @param input - name of input text file
	 * @return the list of tree
	 * @throws IOException
	 */
	private ArrayList<HuffmanTree<Character>> makeForest(String input) throws IOException{
		FileInputStream in = null;
		boolean isDuplicate;
		in = new FileInputStream(input);
		ArrayList<HuffmanTree<Character>> forest = new ArrayList<HuffmanTree<Character>>();
		try {
			int c;
			while((c = in.read()) != -1){
				isDuplicate = false;
				HuffmanTree<Character> instant = new HuffmanTree<Character>((char)c);
				for(HuffmanTree<Character> node: forest){
					if(node.getRoot().getElement().equals(instant.getRoot().getElement())){
						node.getRoot().incFrequency();
						isDuplicate = true;
					}
				}
				if(!isDuplicate){
					forest.add(instant);
				}
			}
		}catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}catch (IOException e) {
			System.out.println(e.toString());
		}finally {
			if (in != null) {
				in.close();
			}
		}
		return forest;
	}

	public static void main(String args[])
	{	
		try {
			new Proj3(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}