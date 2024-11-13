<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<style>
.nav-link .fa {
    display: inline;
}
.sidebar a:hover {
    border-radius: 10px;
    background-color: #a7cdf2; 
}
.side-menu li {
    padding: 5px 0; 
}
.nav-link {
    font-size: 0.9rem; 
}
.nav-link i{
    padding-right: 2px;
}
.side-menu span {
    font-size: 1rem;
}
.sidebar {
    background-color: #ffffff !important; 
    width: 250px;
    transition: width 0.3s ease;
    overflow: hidden;
    border-right: 1px solid #dee2e6;
}

.sidebar.collapsed {
    width: 60px ;
}
.sidebar.collapsed .nav-link span {
    display: none; 
}
.sidebar.collapsed .menu-title span {
    display: none;
}
.sidebar.collapsed .side-menu {
    padding: 0;
    margin: 0; 

}
.menu-title span {
    color: #6c757d;
    font-size: 0.8rem; 
    font-weight: 600;
}

</style>

<div class="sidebar p-2" id="sidebar">
    <div class="nav flex-column">
        <ul class="side-menu" style="list-style-type: none; padding-left: 0;">
            <li class="menu-title">
                <span  >HOME</span>
            </li>
            <li class="side-nav-item">
                <a class="nav-link" href="#" style="color: #212529; font-weight: 400">
                    <i class="fa fa-home"></i> <span>Quick Start</span>
                </a>
            </li>
        </ul>
        <ul class="side-menu" style="list-style-type: none; padding-left: 0;">
            <li class="menu-title">
                <span>APPLICATION</span>
            </li>
            <li>
                <a class="nav-link" href="#" style="color: #343a40;">
                    <i class="fa fa-cubes"></i> <span>Providers</span>
                </a>
            </li>
            <li>
                <a class="nav-link" href="#" style="color: #343a40;">
                    <i class="fa fa-users"></i> <span>Customers</span>
                </a>
            </li>
            <li>
                <a class="nav-link" href="#" style="color: #343a40;">
                    <i class="fa fa-th-list"></i> <span>Categories</span>
                </a>
            </li>
            <li>
                <a class="nav-link" href="#" style="color: #343a40;">
                    <i class="fa fa-book"></i> <span>Products</span>
                </a>
            </li>
            <li>
                <a class="nav-link" href="#" style="color: #343a40;">
                    <i class="fa fa-shopping-cart"></i> <span>Orders</span>
                </a>
            </li>
        </ul>
         <ul class="side-menu" style="list-style-type: none; padding-left: 0;">
            <li class="menu-title">
                <span>ADMIN</span>
            </li>
            <li>
                <a class="nav-link" href="#" style="color: #343a40;">
                    <i class="fa fa-user"></i> <span>Users</span>
                </a>
            </li>
            <li>
                <a class="nav-link" href="#" style="color: #343a40;">
                   <i class="fa fa-user-check"></i><span>Roles</span>
                </a>
            </li>
            <li>
                <a class="nav-link" href="#" style="color: #343a40;">
                    <i class="fa fa-shield-alt fa-fw"></i>
<span>Permissions</span>
                </a>
            </li>
        </ul>
    </div>
</div>

<script>
function toggleSidebar() {
    const sidebar = document.getElementById("sidebar");
    sidebar.classList.toggle("collapsed");
}
</script>


