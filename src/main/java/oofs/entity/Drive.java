package oofs.entity;

import java.util.Map;

import oofs.modules.FileSystemEntityTypeEnum;

public class Drive extends ContainerEntity {

	public Drive(String name, FileSystemEntityTypeEnum type )
	{
		super(name, type, "/");
	}
	
	@Override
	public int getSize() {
        // size is the sum of the entities that are contained
		Map<String, FileSystemEntity> theMap = getContainerEntityMap();
		
		int theSize = theMap.entrySet().stream().map(Map.Entry::getValue).mapToInt(e -> e.getSize()).sum();

		return theSize;
	}

}
