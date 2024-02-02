package oofs;

import java.util.Map;
import oofs.entity.ContainerEntity;
import oofs.entity.FileSystemEntity;
import oofs.modules.FileSystemEntityTypeEnum;

/**
 * Root of OOSFS, containing only Drive (s)
 **/
public class ObjectFileSystemRoot extends ContainerEntity  {

	private static ObjectFileSystemRoot _instance = null;
	
	private ObjectFileSystemRoot()
	{
		super("\\", FileSystemEntityTypeEnum.Root, "\\");
	}
	
	public static synchronized ObjectFileSystemRoot instance()
	{
		if( _instance == null )
		{
			_instance = new ObjectFileSystemRoot();
		}
		return _instance;
	}
	
	private String getDriveName(String path)
	{
		String pathElem[] = path.split("\\\\");
		String driveName = pathElem[1];

		return driveName;
	}
		
	private String getObjectName(String path)
	{
		String pathElem[] = path.split("\\\\");
		String objectName = pathElem[pathElem.length -1];

		return objectName;
	}


	/**
	 * Create an entity, drives are always created at the bottom of the tree.
	 * if the first level is specified as a non-drive type throw an Exception
	 **/
	public FileSystemEntity create(String name, FileSystemEntityTypeEnum type, String path ) throws Exception
	{
		String driveName = getDriveName(path);
		
		Map<String, FileSystemEntity> theMap = getContainerEntityMap();
		if( theMap.get(driveName) == null)
		{
			// create a drive
			addEntity(driveName, FileSystemEntityTypeEnum.Drive, "\\");
		}
		return addEntity(name,type,path);
	}
		
	public void move(String sourcePath, String destPath ) throws Exception
	{
		checkPath(sourcePath);
		checkPath(destPath);
		String name = getObjectName(sourcePath);

        // size is the sum of the entities that are contained
		
		Map<String,FileSystemEntity> srcFsMap = pseudoRecurse(getContainerEntityMap(), sourcePath);
		FileSystemEntity fse = srcFsMap.get(name);
		
		Map<String,FileSystemEntity> destFsMap = pseudoRecurse(getContainerEntityMap(), destPath);
		destFsMap.put(name, fse);
		fse.setPath(destPath);
	}
	
	public String getFsPath(String sourcePath) throws Exception 
	{
		checkPath(sourcePath);

		String name = getObjectName(sourcePath);

        // size is the sum of the entities that are contained
		
		Map<String,FileSystemEntity> srcFsMap = pseudoRecurse(getContainerEntityMap(), sourcePath);
		FileSystemEntity fse = srcFsMap.get(name);
		
		return fse.getPath();
	}

	@Override
	public int getSize() {
        // size is the sum of the entities that are contained
		Map<String, FileSystemEntity> theMap = getContainerEntityMap();
		
		int theSize = theMap.entrySet().stream().map(Map.Entry::getValue).mapToInt(e -> e.getSize()).sum();

		return theSize;
	}
	
	public static void main(String []args) {
		int a[] = {1, 2, -4, 5, 6};
		int b[] = {1, 2, -1, 5, 6};
		int c[] = {-1, -2, -1, -5, -6};

		System.out.println(maxSum(a));
		System.out.println(maxSum(b));
		System.out.println(maxSum(c));
	}

	public static int maxSum(int []v)
	{
		int max = Integer.MIN_VALUE;
		for(int k=0;k<v.length;k++)
		{
			int trial= 0;
			for(int i=k;i<v.length;i++)
			{
				trial +=v[i];
				if( trial > max ) max =  trial;
			}
		}
		
		return max;		
	}
}
