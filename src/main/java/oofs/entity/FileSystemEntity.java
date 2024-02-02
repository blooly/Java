/**
 * 
 */
package oofs.entity;

import oofs.modules.FileSystemEntityTypeEnum;

/**
 * @author andre
 *
 */
public abstract class FileSystemEntity {

	private String name;
	private FileSystemEntityTypeEnum type;
	private String path;
	
	public FileSystemEntity(String name, FileSystemEntityTypeEnum type, String path )
	{
		this.name = name;
		this.type = type;
		this.path = path;
	}	
	
	public void setName(String name )
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public FileSystemEntityTypeEnum getType()
	{
		return this.type;
	}
		
	public void setPath(String path)
	{
		this.path = path;
	}

	public String getPath()
	{
		return this.path;
	}
	
	public abstract int getSize();
}
