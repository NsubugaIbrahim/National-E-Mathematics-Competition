<style>
  .main-sidebar {
    position: fixed;
    top: 0;
    left: 0;
    height: 100%; /* Ensure sidebar stretches to full height of viewport */
    z-index: 1030; /* Adjust as needed */
  }

  .content-wrapper {
    margin-left: 250px; /* Adjust this value to match the width of your sidebar */
    /* If your sidebar width is dynamic, consider using JavaScript to adjust margin-left dynamically */
  }

  .nav-link.active {
    background-color: green !important;
    color: white !important;
  }

  .nav-link.active .nav-icon {
    color: white !important;
  }

  .nav-icon-container {
    position: relative;
    display: inline-block;
  }

  .nav-icon-container .fa-user {
    position: relative;
    z-index: 1; /* Ensure the user icon is on top */
    font-size: 1em; /* Adjust size as needed */
  }

  .nav-icon-container .fa-shield-alt {
    position: absolute;
    top: 0; /* Adjust top position as needed */
    left: 0.7em; /* Adjust left position to reduce separation */
    z-index: 0; /* Ensure the shield icon is behind the user icon */
    font-size: 0.7em; /* Adjust size as needed */
    /* color: white; Optional: Change the color to differentiate the shield */
  }

  /* Add margin to the shield icon or the text to increase separation */
  .nav-icon-container {
    margin-right: 10px; /* Adjust value to increase separation */
  }

  .nav-link p {
    display: inline-block; /* Ensure the text is aligned properly */
    margin: 0; /* Reset default margins */
    margin-left: 15px; /* Add left margin to increase separation */
  }
</style>


  <!-- Navbar -->
  <nav class="main-header navbar navbar-expand navbar-white navbar-light">
    <!-- Left navbar links -->
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
      </li>
      
    </ul>

    <!-- Right navbar links -->
    <ul class="navbar-nav ml-auto">
      <!-- Navbar Search -->
     
      <!-- Messages Dropdown Menu -->
      <li class="nav-item dropdown">
        <a class="nav-link" data-toggle="dropdown" href="#">
          <i class="far fa-comments"></i>
          <span class="badge badge-danger navbar-badge">3</span>
        </a>
        <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
          <a href="#" class="dropdown-item">
            <!-- Message Start -->
            <div class="media">
              <img src="{{ url('public/dist/img/user1-128x128.jpg')}}" alt="User Avatar" class="img-size-50 mr-3 img-circle">
              <div class="media-body">
                <h3 class="dropdown-item-title">
                  Brad Diesel
                  <span class="float-right text-sm text-danger"><i class="fas fa-star"></i></span>
                </h3>
                <p class="text-sm">Call me whenever you can...</p>
                <p class="text-sm text-muted"><i class="far fa-clock mr-1"></i> 4 Hours Ago</p>
              </div>
            </div>
            <!-- Message End -->
          </a>
          <div class="dropdown-divider"></div>
          <a href="#" class="dropdown-item">
            <!-- Message Start -->
            <div class="media">
              <img src="{{asset('dist/img/user8-128x128.jpg')}}" alt="User Avatar" class="img-size-50 img-circle mr-3">
              <div class="media-body">
                <h3 class="dropdown-item-title">
                  John Pierce
                  <span class="float-right text-sm text-muted"><i class="fas fa-star"></i></span>
                </h3>
                <p class="text-sm">I got your message bro</p>
                <p class="text-sm text-muted"><i class="far fa-clock mr-1"></i> 4 Hours Ago</p>
              </div>
            </div>
            <!-- Message End -->
          </a>
          <div class="dropdown-divider"></div>
          <a href="#" class="dropdown-item">
            <!-- Message Start -->
            <div class="media">
              <img src="{{asset('dist/img/user3-128x128.jpg')}}" alt="User Avatar" class="img-size-50 img-circle mr-3">
              <div class="media-body">
                <h3 class="dropdown-item-title">
                  Nora Silvester
                  <span class="float-right text-sm text-warning"><i class="fas fa-star"></i></span>
                </h3>
                <p class="text-sm">The subject goes here</p>
                <p class="text-sm text-muted"><i class="far fa-clock mr-1"></i> 4 Hours Ago</p>
              </div>
            </div>
            <!-- Message End -->
          </a>
          <div class="dropdown-divider"></div>
          <a href="#" class="dropdown-item dropdown-footer">See All Messages</a>
        </div>
      </li>
      <!-- Notifications Dropdown Menu -->
      <li class="nav-item dropdown">
        <a class="nav-link" data-toggle="dropdown" href="#">
          <i class="far fa-bell"></i>
          <span class="badge badge-warning navbar-badge">15</span>
        </a>
        <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
          <span class="dropdown-item dropdown-header">15 Notifications</span>
          <div class="dropdown-divider"></div>
          <a href="#" class="dropdown-item">
            <i class="fas fa-envelope mr-2"></i> 4 new messages
            <span class="float-right text-muted text-sm">3 mins</span>
          </a>
          <div class="dropdown-divider"></div>
          <a href="#" class="dropdown-item">
            <i class="fas fa-users mr-2"></i> 8 friend requests
            <span class="float-right text-muted text-sm">12 hours</span>
          </a>
          <div class="dropdown-divider"></div>
          <a href="#" class="dropdown-item">
            <i class="fas fa-file mr-2"></i> 3 new reports
            <span class="float-right text-muted text-sm">2 days</span>
          </a>
          <div class="dropdown-divider"></div>
          <a href="#" class="dropdown-item dropdown-footer">See All Notifications</a>
        </div>
      </li>
      <li class="nav-item">
        <a class="nav-link" data-widget="fullscreen" href="#" role="button">
          <i class="fas fa-expand-arrows-alt"></i>
        </a>
      </li>
      <li class="nav-item">
        <a class="nav-link" data-widget="control-sidebar" data-controlsidebar-slide="true" href="#" role="button">
          <i class="fas fa-th-large"></i>
        </a>
      </li>
    </ul>
  </nav>
  <!-- /.navbar -->

  <!-- Main Sidebar Container -->
<aside class="main-sidebar sidebar-dark-primary elevation-4">
  <!-- Brand Logo -->
  <a href="index3.html" class="brand-link">
    <img src="{{ asset('dist/img/AdminLTELogo.png') }}" alt="AdminLTE Logo" class="brand-image img-circle elevation-3" style="opacity: .8">
    <span class="brand-text font-weight-light">Math Billiard</span>
  </a>

  <!-- Sidebar -->
  <div class="sidebar">
    <!-- Sidebar user panel (optional) -->
    <div class="user-panel mt-3 pb-3 mb-3 d-flex">
    <div class="image">
        <img src="{{ asset('dist/img/user2-160x160.jpg') }}" class="img-circle elevation-2" alt="User Image">
    </div>
    <div class="info">
        @if(Auth::check())
            <a href="#" class="d-block">{{ Auth::user()->name }}</a>
        @else
        @endif
    </div>
</div>


    <!-- Sidebar Menu -->
    <nav class="mt-2">
      <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
        <li class="nav-item">
          <a href="{{ url('admin/dashboard') }}" class="nav-link {{ Route::is('admin.dashboard') ? 'active' : '' }}">
            <i class="nav-icon fas fa-tachometer-alt"></i>
            <p>Dashboard</p>
          </a>
        </li>
        <li class="nav-item">
          <a href="{{ url('admin/admin/analytics') }}" class="nav-link {{ Route::is('admin.analytics') ? 'active' : '' }}">
            <i class="nav-icon fas fa-chart-line"></i>
            <p>Analytics</p>
          </a>
        </li>
        <li class="nav-item">
          <a href="{{ url('admin/admin/list') }}" class="nav-link {{ Route::is('admin.list') ? 'active' : '' }}">
            <span class="nav-icon-container">
              <i class="nav-icon fas fa-user"></i>
              <i class="nav-icon fas fa-shield-alt"></i>
            </span>
            <p>Admin</p>
          </a>
        </li>

        <li class="nav-item">
          <a href="{{ url('admin/schools/list') }}" class="nav-link {{ Route::is('admin.schools.list') ? 'active' : '' }}">
          <i class="fas fa-school"></i>
            <p>Schools</p>
          </a>
        </li>

        <li class="nav-item">
          <a href="{{ url('admin/school_rep/list') }}" class="nav-link {{ Route::is('admin.school_rep.list') ? 'active' : '' }}">
          <i class="fas fa-user-graduate"></i>
            <p>School Representatives</p>
          </a>
        </li>        

        <li class="nav-item">
          <a href="{{ url('admin/student/list') }}" class="nav-link {{ Route::is('admin.student.list') ? 'active' : '' }}">
            <i class="nav-icon fas fa-users"></i>
            <p>Student</p>
          </a>
        </li>
        <li class="nav-item has-treeview {{ Request::is('pages/charts/*') ? 'menu-open' : '' }}">
          <a href="#" class="nav-link {{ Request::is('pages/charts/*') ? 'active' : '' }}">
            <i class="nav-icon fas fa-chart-pie"></i>
            <p>Charts<i class="right fas fa-angle-left"></i></p>
          </a>
          <ul class="nav nav-treeview">
            <li class="nav-item">
              <a href="{{ url('pages/charts/chartjs') }}" class="nav-link {{ Request::is('pages/charts/chartjs') ? 'active' : '' }}">
                <i class="far fa-circle nav-icon"></i>
                <p>ChartJS</p>
              </a>
            </li>
            <li class="nav-item">
              <a href="{{ url('pages/charts/flot') }}" class="nav-link {{ Request::is('pages/charts/flot') ? 'active' : '' }}">
                <i class="far fa-circle nav-icon"></i>
                <p>Flot</p>
              </a>
            </li>
            <li class="nav-item">
              <a href="{{ url('pages/charts/inline') }}" class="nav-link {{ Request::is('pages/charts/inline') ? 'active' : '' }}">
                <i class="far fa-circle nav-icon"></i>
                <p>Inline</p>
              </a>
            </li>
            <li class="nav-item">
              <a href="{{ url('pages/charts/uplot') }}" class="nav-link {{ Request::is('pages/charts/uplot') ? 'active' : '' }}">
                <i class="far fa-circle nav-icon"></i>
                <p>uPlot</p>
              </a>
            </li>
          </ul>
        </li>
        <li class="nav-item has-treeview {{ Request::is('admin/exams*') ? 'menu-open' : '' }}">
          <a href="#" class="nav-link {{ Request::is('admin/exams*') ? 'active' : '' }}">
            <i class="nav-icon fas fa-file-alt"></i>
            <p>Examinations<i class="right fas fa-angle-left"></i></p>
          </a>
          <ul class="nav nav-treeview">
            <li class="nav-item">
              <a href="{{ url('admin/exam/list') }}" class="nav-link {{ Request::is('admin/exam*') ? 'active' : '' }}">
              <i class="nav-icon fas fa-clipboard"></i>
                <p>Exam List</p>
              </a>
            </li>

            <li class="nav-item">
              <a href="{{ url('admin/exam/exam_schedule') }}" class="nav-link {{ Request::is('admin/exam*') ? 'active' : '' }}">
              <i class="nav-icon fas fa-pencil-alt"></i>
                <p>Exam Schedule</p>
              </a>
            </li>
            <!-- Add other examination related links here -->
          </ul>
        </li>
        <li class="nav-item">
          <a href="{{ route('logout') }}" class="nav-link" onclick="confirmLogout(event)">
            <i class="nav-icon fas fa-arrow-circle-left"></i>
            <p>Logout</p>
          </a>
        </li>
      </ul>
    </nav>
    <!-- /.sidebar-menu -->
  </div>
  <!-- /.sidebar -->
</aside>

  @if (session('success'))
    <div class="alert-overlay">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            {{ session('success') }}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close" onclick="closeAlert()">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </div>

    <style>
        .alert-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 1050; /* Higher than Bootstrap's modal z-index */
        }
        .alert {
            max-width: 500px;
            width: 100%;
            margin: 0 20px;
        }
    </style>

    <script>
        function closeAlert() {
            document.querySelector('.alert-overlay').style.display = 'none';
        }

        // Auto-hide the alert after a certain time (optional)
        setTimeout(() => {
            closeAlert();
        }, 5000); // 5 seconds
    </script>
@endif







     