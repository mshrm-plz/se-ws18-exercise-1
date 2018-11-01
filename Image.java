/******************************************************************************************
** Class: Image.java
** Authors: Thao Nguyen (120392) & Lisa Titz (120003)
** We used the following sources for help:
** https://stackoverflow.com/questions/36804391/how-to-convert-int-rgb-into-byte-2d-array-gray-image
** https://stackoverflow.com/questions/4775457/writing-an-array-to-a-file
** https://stackoverflow.com/questions/7276363/unsigned-bytes-in-java
** https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
** https://www.cs.swarthmore.edu/~soni/cs35/f13/Labs/extras/01/ppm_info.html
******************************************************************************************/

import java.io.*;

public class Image {

	// define image width and height, as well as a 1D array to store the image data
	private int width, height;
	public byte[] data;

	// create an image object with the specified width and height
	public Image(int width, int height) {
		this.width = width;
		this.height = height;
		data = new byte[height*width*3];	// 1D array that can store up to height*width pixels (3 bytes per pixel)
	}

	// set a pixel at position x, y to the RGB value represented by val
	public byte[] set(int x, int y, int val) {
		if (x >= height || y >= width*3) {	// x & y have to be within the range of the array
			System.out.println("Invalid input");
		}
		else {
			// Reminder: data is a 1D array
			// Operation (x * width + y) gives us access to every row of the image (each row is offset by the width)
			// Similarly, (... * 3) because there are three bytes per pixel
			// The right shift operator allows us to read the individual R-G-B components of val
			data[(x*width+y)*3] = (byte) (val >> 16);
			data[(x*width+y)*3 + 1] = (byte) (val >> 8);
			data[(x*width+y)*3 + 2] = (byte) (val);
		}
		return data;
	}

	// write the image data to a file
	public void write(String fileName) {
		PrintWriter printWriter;
		try {
			printWriter = new PrintWriter(new FileOutputStream(fileName, true));
			System.out.println("Writing to file...");
			printWriter.println("P3"); // Image format
			printWriter.println(width + " " + height); // #columns and #rows in the image
			printWriter.println(255); // Maximum color value
			
			for (int i = 0; i < height*width*3; i++) {
				// Bitwise '&' operator lets us convert signed bytes to unsigned
				// Since the byte data type in Java has the range of [-128, 127] and R-G-B values can't be negative
				printWriter.print((data[i]&0xFF) + " ");	// Bitwise '&' operator converts signed bytes to unsigned
			}
			printWriter.close();
		}
		catch(IOException e) {
			System.out.print(e.getMessage());
		}
	}
}

/******************************************************************************************
** End of program
******************************************************************************************/
