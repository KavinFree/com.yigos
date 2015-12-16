root : {
        	id : 'root',
            text : 'root',
            expanded: true,
	        children : [
	        {
	        	id : 'system',
	            text : '系统管理',
	            expanded: true,
	            children : [
	            	{
		        	id : 'auth',
		            text : '权限管理',
		            expanded: true,
		            children : [
			            {
			            	id : 'user',
			                text : '用户管理',
			                leaf : true,
			                urlPath : '/auth/user/User/page.html'
			            }, {
			            	id : 'role',
			                text : '角色管理',
			                leaf : true,
			                urlPath : '/auth/role/Role/page.html'
			            }, {
			            	id : 'menu',
			                text : '功能管理',
			                leaf : true,
			                urlPath : '/auth/menu/Menu/page.html'
			            }, {
			            	id : 'role-user',
			                text : '角色用户',
			                leaf : true,
			                urlPath : '/auth/roleuser/RoleUser/page.html'
			            }, {
			            	id : 'role-auth',
			                text : '角色授权',
			                leaf : true,
			                urlPath : '/auth/roleuser/RoleUser/page.html'
			            }
		            ]
	            	},
		            {
			            id : 'config',
			            text : '字典数据管理',
			            expanded: true,
			            children : [
			            {
			            	id : 'config-type',
			                text : '字典类型管理',
			                leaf : true,
			                urlPath : '/auth/config/Type/page.html'
			            },
			            {
			            	id : 'config-param',
			                text : '字典明细管理',
			                leaf : true,
			                urlPath : '/auth/config/Param/page.html'
			            }
			            ]
			        }
		        ]
		    },
		    {
		    	id : 'cms',
                text : 'cms',
                expanded: true,
	            children : [
	            {
	            	id : 'cms-type',
	                text : 'cms类型管理',
	                leaf : true,
	                urlPath : '/auth/user/User/page.html'
	            },
	            {
	            	id : 'cms-article',
	                text : 'cms文章管理',
	                checked: false,
	                leaf : true,
	                urlPath : '/auth/user/User/page.html'
	            }
	            ]
		    }]
	    }