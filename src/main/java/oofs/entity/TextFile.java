package oofs.entity;

import oofs.modules.FileSystemEntityTypeEnum;

public class TextFile extends FileSystemEntity {

	private String content="";

	public TextFile(String name, FileSystemEntityTypeEnum type, String path )
	{
		super(name, type, path);
	}

	@Override
	public int getSize() {
		
		return this.content.length();
	}
	
	@Override
	public String getPath()
	{
		return super.getPath() + "\\" + this.getName();
	}
	
	public void writeToFile(String content)
	{
		if( content == null )
		{
			content = "";
		}
		this.content = content;
	}
	
	public String readFromFile()
	{
		return this.content;
	}

}
