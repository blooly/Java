/**
 * 
 */
package oofs.entity;

import java.util.Hashtable;
import java.util.Map;

import oofs.modules.FileSystemEntityFactory;
import oofs.modules.FileSystemEntityTypeEnum;

/**
 * ContainerEntity is the base class of all container objects in the OOFS. Containers 
 * 
 * @author andrew
 *
 */
public abstract class ContainerEntity extends FileSystemEntity {
	protected Map<String,FileSystemEntity> entityMap = new Hashtable<String,FileSystemEntity>(); // disallow nulls
	
	protected ContainerEntity(String name, FileSystemEntityTypeEnum type, String path )
	{
		super(name, type, path);
	}
	
	protected Map<String,FileSystemEntity> getContainerEntityMap()
	{
		return entityMap;
	}
	
	protected void checkName(String name ) throws Exception
	{
		if((name == null) || (name.length()==0))
		{
			throw new Exception("Bad data in addEntity");			
		}
	}
	
	protected void checkPath( String parentPath) throws Exception
	{
		// insure path exists
		if( (parentPath == null)  || (parentPath.length() == 0) || parentPath.charAt(0) != '\\')
		{
			throw new Exception("Bad data in addEntity");			
		}		
	}
	
	protected void checkData(String name, String parentPath) throws Exception
	{
		checkName(name);		
	}
	
	public Map<String,FileSystemEntity> pseudoRecurse(Map<String,FileSystemEntity> tip, String path) throws Exception
	{
		String pathElem[] = path.split("\\\\");
		int maxDepth = pathElem.length - 1;
		
		Map<String,FileSystemEntity> traversal;
		if( pathElem.length > 0)
		{
			int i = 1; // 1st slash
			String elemName = pathElem[i];
			
			traversal = entityMap;
			
			while( i < maxDepth )
			{
				FileSystemEntity entity = traversal.get(elemName);
				
				if( (entity == null) || (entity instanceof TextFile) )
				{
					break;
				}
				
				traversal = ((ContainerEntity)entity).getContainerEntityMap();			
				i++;
			}
		}
		else
		{
			traversal = entityMap;
		}

		return traversal;
	}
	
	//
	public FileSystemEntity addEntity( String name, FileSystemEntityTypeEnum type, String parentPath ) throws Exception
	{
		checkData( name,  parentPath);
		
		Map<String,FileSystemEntity> traversal = pseudoRecurse(entityMap, parentPath);
		// good, create it
		
		FileSystemEntity addEntity = FileSystemEntityFactory.create(name,type,parentPath);
		traversal.put(name, addEntity);		// add node
		
		return addEntity;
	}
	
	public void deleteEntity( String fullPath ) throws Exception
	{
		checkPath( fullPath);	
		int nameIdx = fullPath.lastIndexOf('\\') - 1;
		String parentPath = fullPath.substring(0, nameIdx); // chop off the path
		String name = fullPath.substring(nameIdx,parentPath.length()-1);
		
		Map<String,FileSystemEntity> traversal = pseudoRecurse(entityMap, fullPath);
		
		traversal.remove(name);
	}
	
	public void moveEntity( String sourcePath, String destPath ) throws Exception
	{
		checkPath( sourcePath);
		checkPath( destPath);
		
		Map<String,FileSystemEntity> srcEntity = pseudoRecurse(entityMap, sourcePath);
		String srcPathElem[] = sourcePath.split("\\\\");

		String name = srcPathElem[srcPathElem.length-1];
		FileSystemEntity fse = srcEntity.get(name);
		
		Map<String,FileSystemEntity> destEntity = pseudoRecurse(entityMap, destPath);
		destEntity.put(name, fse);		
	}

}
