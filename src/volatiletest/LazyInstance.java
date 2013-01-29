package volatiletest;


public abstract class LazyInstance
{
  protected volatile Object field;

  protected abstract Object getInitializedObject();
  
  public Object getObject()
  {
      return getInitializedObject();
  }
}
