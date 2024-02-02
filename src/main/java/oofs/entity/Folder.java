package oofs.entity;

import java.util.Map;

import oofs.modules.FileSystemEntityFactory;
import oofs.modules.FileSystemEntityTypeEnum;

public class Folder extends ContainerEntity {

	public Folder(String name, FileSystemEntityTypeEnum type, String path )
	{
		super(name, type, path);
	}

	@Override
	public int getSize() {

		Map<String, FileSystemEntity> theMap = getContainerEntityMap();
		
		int theSize = theMap.entrySet().stream().map(Map.Entry::getValue).mapToInt(e -> e.getSize()).sum();

		return theSize;
	}

	public static void main( String[] args) throws Exception
	{
		Folder f = (Folder)FileSystemEntityFactory.create("tmp", FileSystemEntityTypeEnum.Folder, "\\tmp\\pmt");
				
		TextFile tf1 = (TextFile)f.addEntity("tf1", FileSystemEntityTypeEnum.TextFile, "\\tmp");		
		tf1.writeToFile("SomeText");
		
		TextFile tf2 = (TextFile)f.addEntity("tf2", FileSystemEntityTypeEnum.TextFile, "\\tmp");
		tf2.writeToFile("MoreText");
			
		System.out.println(f.getSize());
	}
}
