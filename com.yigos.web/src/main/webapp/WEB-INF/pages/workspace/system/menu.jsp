<html>
<head>
<script>
require(["dojo/parser", "dojo/store/Memory", "dojo/query!css2",
"dijit/Menu", "dijit/MenuItem", "dijit/tree/ObjectStoreModel", "dijit/Tree"]);
</script>

</head>
<body class="${dojoTheme}">
	<div data-dojo-type="dojo/store/Memory" data-dojo-id="myStore">
		<!-- Create store with inlined data.
        For larger data sets should use dojo.store.JsonRest etc. instead of dojo.store.Memory. -->
		<script type="dojo/method">
         this.setData([
            { id: 'world', name:'root', type:'planet', population: '6 billion'},
            { id: 'AF', name:'Africa', type:'continent', population:'900 million', area: '30,221,532 sq km',
                    timezone: '-1 UTC to +4 UTC', parent: 'world'},
                { id: 'EG', name:'Egypt', type:'country', parent: 'AF' },
                { id: 'KE', name:'Kenya', type:'country', parent: 'AF' },
                    { id: 'Nairobi', name:'Nairobi', type:'city', parent: 'KE' },
                    { id: 'Mombasa', name:'Mombasa', type:'city', parent: 'KE' },
                { id: 'SD', name:'Sudan', type:'country', parent: 'AF' },
                    { id: 'Khartoum', name:'Khartoum', type:'city', parent: 'SD' },
            { id: 'AS', name:'Asia', type:'continent', parent: 'world' },
                { id: 'CN', name:'China', type:'country', parent: 'AS' },
                { id: 'IN', name:'India', type:'country', parent: 'AS' },
                { id: 'RU', name:'Russia', type:'country', parent: 'AS' },
                { id: 'MN', name:'Mongolia', type:'country', parent: 'AS' },
            { id: 'OC', name:'Oceania', type:'continent', population:'21 million', parent: 'world'},
            { id: 'EU', name:'Europe', type:'continent', parent: 'world' },
                { id: 'DE', name:'Germany', type:'country', parent: 'EU' },
                { id: 'FR', name:'France', type:'country', parent: 'EU' },
                { id: 'ES', name:'Spain', type:'country', parent: 'EU' },
                { id: 'IT', name:'Italy', type:'country', parent: 'EU' },
            { id: 'NA', name:'North America', type:'continent', parent: 'world' },
            { id: 'SA', name:'South America', type:'continent', parent: 'world' }
        ]);
    </script>
		<script type="dojo/method" data-dojo-event="getChildren" data-dojo-args="object">
         return this.query({parent: object.id});
    </script>
	</div>
	<!-- Create the model bridging the store and the Tree -->
	<div data-dojo-type="dijit/tree/ObjectStoreModel"
		data-dojo-id="myModel"
		data-dojo-props="store: myStore, query: {id: 'world'}"></div>
	<!-- Create the tree -->
	<div data-dojo-type="dijit/Tree" id="menuTree"
		data-dojo-props="model: myModel, showRoot: true, openOnClick: true">
	</div>
	<ul id="menuTree_menu" data-dojo-type="dijit/Menu"
		data-dojo-props='style:"display: none;", targetNodeIds: ["menuTree"], selector: ".dijitTreeNode"'>
		<li data-dojo-type="dijit/MenuItem">
		<script type="dojo/connect" data-dojo-event="onClick">
                            // get a hold of the dijit.TreeNode that was the source of this open event
                            var tn = dijit.byNode(this.getParent().currentTarget);
							//dojo.addTab("mainTabContainer", "http://www.baidu.com", "Editor", true);
        </script></li>
	</ul>
</body>
</html>