package oofs.entity;

import java.util.Map;

import oofs.modules.FileSystemEntityTypeEnum;

public class ZipFile extends ContainerEntity {

	public ZipFile(String name, FileSystemEntityTypeEnum type, String path )
	{
		super(name, type, path);
	}

	@Override
	public int getSize() {
        // size is the sum/2 of the entities that are contained
		Map<String, FileSystemEntity> theMap = getContainerEntityMap();
		
		int theSize = theMap.entrySet().stream().map(Map.Entry::getValue).mapToInt(e -> e.getSize()).sum()/2;

		return theSize;
	}

}
