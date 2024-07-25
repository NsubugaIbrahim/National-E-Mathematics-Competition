<head> 
<style>
  .logo1{
    display: flex;
    justify-content: center;
    position: relative;
     top: -40px;
     }
     .logo{
    margin-top: -60px;
    }
    .nav a:hover {
        background-color: #852a90; 
        color: #AAAAAA; 
    }
</style>
</head>
<div class="sidebar">
    <div class="sidebar-wrapper">
    <div class="logo1"><img src="{{ asset('black/img/Logo2.png') }}" alt="Logo" width="300" height="300"></div>
        <div class="logo">
            <a href="#" class="simple-text logo-mini">{{ __('') }}</a>
            <a href="#" class="simple-text logo-normal">{{ __('') }}</a>
        </div>
        <ul class="nav">
            <li @if ($pageSlug == 'dashboard') class="active " @endif>
                <a href="{{ route('home') }}">
                    <i class="tim-icons icon-chart-pie-36"></i>
                    <h4>{{ __('Dashboard') }}</h4>
                </a>
            </li>
            
            <li @if ($pageSlug == 'reps') class="active " @endif>
                <a href="{{ route('pages.reps') }}">
                <i class="tim-icons icon-paper"></i>
                    <h4>{{ __('Validate Representatives') }}</h4>
                </a>
            </li>
            <li @if ($pageSlug == 'schools') class="active " @endif>
                <a href="{{ route('pages.schools') }}">
                    <i class="tim-icons icon-notes"></i>
                    <h4>{{ __('Register Schools') }}</h4>
                </a>
            </li>
            <li @if ($pageSlug == 'participants') class="active " @endif>
                <a href="{{ route('participants') }}">
                <i class="tim-icons icon-single-02"></i>
                    <h4>{{ __('participants') }}</h4>
                </a>
            </li>
            <li @if ($pageSlug == 'upload') class="active " @endif>
                <a href="{{ route('pages.upload') }}">
                <i class="tim-icons icon-upload"></i>
                    <h4>{{ __('Upload Questions') }}</h4>
                </a>
            </li>
            <li @if ($pageSlug == 'challenges') class="active " @endif>
                <a href="{{ route('pages.challenges') }}">
                    <i class="tim-icons icon-components"></i>
                    <h4>{{ __('Generate Challenges') }}</h4>
                </a>
            </li>
            <li @if ($pageSlug == 'analytics') class="active " @endif>
                <a href="{{ route('analytics') }}">
                    <i class="tim-icons icon-chart-bar-32"></i>
                    <h4>{{ __('Analytics') }}</h4>
                </a>
            </li>
        </ul>
    </div>
</div>
