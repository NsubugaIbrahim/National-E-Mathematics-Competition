<head> 
<style>
  .logo1{
    display: flex;
    justify-content: center;
    position: relative;
     top: 0px;
     }
     .logo{
    margin-top: -30px;
    }
    .nav a:hover {
        background-color: #852a90; 
        color: #AAAAAA; 
    }
    .sidebar h5{
        color: yellow;
        
    font-weight: bold;
    }
</style>
</head>
<div class="sidebar">
    <div class="sidebar-wrapper">
        <div class="logo1"><img src="{{ asset('black/img/logo12.png') }}" alt="Logo" width="200" height="130"></div>
        <div class="logo">
            <a href="#" class="simple-text logo-mini">{{ __('') }}</a>
            <a href="#" class="simple-text logo-normal">{{ __('') }}</a>
        </div>
        <ul class="nav">
            <li @if ($pageSlug == 'dashboard') class="active " @endif>
                <a href="{{ route('home') }}">
                    <i class="tim-icons icon-chart-pie-36"></i>
                    <h5>{{ __('Dashboard') }}</h5>
                </a>
            </li>
            
            <li @if ($pageSlug == 'reps') class="active " @endif>
                <a href="{{ route('pages.reps') }}">
                <i class="tim-icons icon-paper"></i>
                    <h5>{{ __('Validate Representatives') }}</h5>
                </a>
            </li>
            <li @if ($pageSlug == 'schools') class="active " @endif>
                <a href="{{ route('pages.schools') }}">
                    <i class="tim-icons icon-notes"></i>
                    <h5>{{ __('Register Schools') }}</h5>
                </a>
            </li>
            <li @if ($pageSlug == 'participants') class="active " @endif>
                <a href="{{ route('participants') }}">
                <i class="tim-icons icon-single-02"></i>
                    <h5>{{ __('participants') }}</h5>
                </a>
            </li>
            <li @if ($pageSlug == 'upload') class="active " @endif>
                <a href="{{ route('pages.upload') }}">
                <i class="tim-icons icon-upload"></i>
                    <h5>{{ __('Upload Questions') }}</h5>
                </a>
            </li>
            <li @if ($pageSlug == 'challenges') class="active " @endif>
                <a href="{{ route('pages.challenges') }}">
                    <i class="tim-icons icon-components"></i>
                    <h5>{{ __('Generate Challenges') }}</h5>
                </a>
            </li>
            <li @if ($pageSlug == 'analytics') class="active " @endif>
                <a href="{{ route('analytics') }}">
                    <i class="tim-icons icon-chart-bar-32"></i>
                    <h5>{{ __('Analytics') }}</h5>
                </a>
            </li>
        </ul>
    </div>
</div>
