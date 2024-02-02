package oofs.modules;


import oofs.entity.Drive;
import oofs.entity.FileSystemEntity;
import oofs.entity.Folder;
import oofs.entity.TextFile;
import oofs.entity.ZipFile;

public class FileSystemEntityFactory {

	public static FileSystemEntity create(String name, FileSystemEntityTypeEnum type, String path) throws Exception
	{
		switch( type )
		{
			case Drive:
			{
				return new Drive(name, type);
			}
			case Folder:
			{
				return new Folder(name, type, path);
			}
			case ZipFile:
			{
				return new ZipFile(name, type, path);
			}
			case TextFile:
			{
				return new TextFile(name, type, path);
			}
			default:
				throw new Exception("Bad object type");
		}
	}
}
