/** 
* Typed Scala.js facade for Vuex.
* https://vuex.vuejs.org
*/
@js.native
class VuexStore extends js.Object {
  val state: js.Object = js.native

  val getters: js.Object = js.native
  
  /** commit(type: string, payload?: any, options?: Object) */
  def commit(`type`: String, payload: js.Object): Unit = js.native

  /** commit(mutation: Object, options?: Object) options can have root: true that allows to commit root mutations in namespaced modules. */
  def commit(mutation: VuexMutation): Unit = js.native

  /** Returns a Promise that resolves all triggered action handlers. */
  def dispatch(`type`: String, payload: js.Any): Unit = js.native

  def dispatch(action: VuexAction): Unit = js.native
  
  /** Replace the store's root state. Use this only for state hydration / time-travel purposes. */
  def replaceState(state: js.Object): Unit = js.native

  /** Reactively watch fn's return value, and call the callback when the value changes. fn receives the store's state as the first argument, and getters as the second argument. 
  * @return To stop watching, call the returned unwatch function.
  */
  def watch(getter: js.Function, cb: js.Function, options: js.Object): Unit = js.native
  /**
  * Subscribe to store mutations. The handler is called after every mutation and receives the mutation descriptor and post-mutation state as arguments
  * * @return To stop subscribing, call the returned unsubscribe function.
  */
  def subscribe(handler: js.Function): Unit = js.native
  
  /**
  * New in 2.5.0
  * Subscribe to store actions. The handler is called for every dispatched action and receives the action descriptor and current store state as arguments:
  */
  def subscribeAction(handler: js.Function): Unit = js.native
  
  /**
  * Register a dynamic module
  */
  def registerModule(path: String, module: VuexModuleOps): Unit = js.native

  def unregisterModule(path: String): Unit = js.native

  /**
  * Hot swap new actions and mutations.
  */
  def hotUpdate(newOptions: js.Object): Unit = js.native
}

@js.native
trait VuexAction extends js.Object {
  def `type`: String
}

@js.native
trait VuexMutation extends js.Object {
  def `type`: String
}

@js.native
trait VuexModuleOps extends js.Object {
  var state: js.Object = js.native

  var mutations: js.Dictionary[js.Function2[VuexStore, js.Object, Unit]] = js.native
  
  var getters: js.Dictionary[js.Function] = js.native

  var actions: js.Dictionary[js.Function1[Any, Unit]] = js.native

  var modules: js.Object = js.native

  var plugins: js.Array[js.Function] = js.native
  
  var namespaced: Boolean = js.native

  /**
  * In strict mode, whenever Vuex state is mutated outside of mutation handlers, an error will be thrown. 
  * This ensures that all state mutations can be explicitly tracked by debugging tools.
  */
  var strict: Boolean = js.native

}
