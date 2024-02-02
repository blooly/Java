package oofs;

import org.junit.Test;

import org.junit.Assert;

import oofs.entity.Drive;
import oofs.entity.FileSystemEntity;
import oofs.entity.Folder;
import oofs.entity.TextFile;
import oofs.entity.ZipFile;
import oofs.modules.FileSystemEntityFactory;
import oofs.modules.FileSystemEntityTypeEnum;


public class ObjectFileSystemTest
{
    @Test
    public void testMove() throws Exception
    {
    	FileSystemEntity folder1 = ObjectFileSystemRoot.instance().create("folder1", FileSystemEntityTypeEnum.Folder, "\\test");
    	FileSystemEntity folder2 = ObjectFileSystemRoot.instance().create("folder2", FileSystemEntityTypeEnum.Folder, "\\test");
    	FileSystemEntity file1 = ObjectFileSystemRoot.instance().create("testFile1", FileSystemEntityTypeEnum.TextFile, "\\test\\folder1");
		
    	Assert.assertEquals("\\test\\folder1\\testFile1", file1.getPath());
    	
    	ObjectFileSystemRoot.instance().move("\\test\\folder1\\testFile1", "\\test\\folder2");
    	
    	//System.out.println(ObjectFileSystemRoot.instance().getFsPath("\\test\\folder2\\testFile1"));
    	
    	Assert.assertTrue(ObjectFileSystemRoot.instance().getFsPath("\\test\\folder2\\testFile1").equals("\\test\\folder2\\testFile1"));
    }

    // For a drive or a folder, size is the sum of all sizes of the entities it contains.
    @Test
    public void testDriveSize() throws Exception
    {
		Drive d = (Drive)FileSystemEntityFactory.create("tmp", FileSystemEntityTypeEnum.Drive, "\\tmp\\pmt");
		
    	String s1 = "SomeText";
		TextFile tf1 = (TextFile)d.addEntity("tf1", FileSystemEntityTypeEnum.TextFile, "\\tmp");		
		tf1.writeToFile(s1);
		
    	String s2 = "MoreText";
		TextFile tf2 = (TextFile)d.addEntity("tf2", FileSystemEntityTypeEnum.TextFile, "\\tmp");
		tf2.writeToFile(s2);		
	
		Assert.assertEquals(d.getSize(), (s1 +s2).length());
    }
    
    // For a drive or a folder, size is the sum of all sizes of the entities it contains.
    @Test
    public void testFolderSize() throws Exception
    {
		Folder d = (Folder)FileSystemEntityFactory.create("tmp", FileSystemEntityTypeEnum.Folder, "\\tmp\\pmt");
		
    	String s1 = "SomeText";
		TextFile tf1 = (TextFile)d.addEntity("tf1", FileSystemEntityTypeEnum.TextFile, "\\tmp");		
		tf1.writeToFile(s1);
		
    	String s2 = "MoreText";
		TextFile tf2 = (TextFile)d.addEntity("tf2", FileSystemEntityTypeEnum.TextFile, "\\tmp");
		tf2.writeToFile(s2);		
	
		Assert.assertEquals(d.getSize(), (s1 +s2).length());
    }

    // For a zip file, size is one half of the sum of all sizes of the entities it contains.
    @Test
    public void testZipFileSize() throws Exception
    {
		ZipFile d = (ZipFile)FileSystemEntityFactory.create("tmp", FileSystemEntityTypeEnum.ZipFile, "\\tmp\\pmt");
		
    	String s1 = "SomeText";
		TextFile tf1 = (TextFile)d.addEntity("tf1", FileSystemEntityTypeEnum.TextFile, "\\tmp");		
		tf1.writeToFile(s1);
		
    	String s2 = "MoreText";
		TextFile tf2 = (TextFile)d.addEntity("tf2", FileSystemEntityTypeEnum.TextFile, "\\tmp");
		tf2.writeToFile(s2);		
	
		Assert.assertEquals(d.getSize(), (s1 +s2).length()/2);
    }
    
    // For a text file � size is the length of its content.
    @Test
    public void testTextFileSize() throws Exception
    {
		Folder f = (Folder)FileSystemEntityFactory.create("tmp", FileSystemEntityTypeEnum.Folder, "\\tmp\\pmt");
		
    	String s1 = "SomeText";
		TextFile tf1 = (TextFile)f.addEntity("tf1", FileSystemEntityTypeEnum.TextFile, "\\tmp");		
		tf1.writeToFile(s1);
			
		Assert.assertEquals(tf1.readFromFile().length(), tf1.getSize());
    }


}
