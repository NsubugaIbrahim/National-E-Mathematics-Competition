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
            <li @if ($pageSlug = 'dashboard') class="active " @endif>
                <a href="{{ route('home') }}">
                    <i class="tim-icons icon-chart-pie-36"></i>
                    <h4>{{ __('Dashboard') }}</h4>
                </a>
            </li>
            <li>
                <a data-toggle="collapse" href="#laravel-examples" aria-expanded="true">
                    <i class="fab fa-laravel" ></i>
                    <span class="nav-link-text" >{{ __('Laravel Examples') }}</span>
                    <b class="caret mt-1"></b>
                </a>

                <div class="collapse show" id="laravel-examples">
                    <ul class="nav pl-4">
                        <li @if ($pageSlug == 'profile') class="active " @endif>
                            <a href="{{ route('profile.edit')  }}">
                                <i class="tim-icons icon-single-02"></i>
                                <p>{{ __('User Profile') }}</p>
                            </a>
                        </li>
                        <li @if ($pageSlug == 'users') class="active " @endif>
                            <a href="{{ route('user.index')  }}">
                                <i class="tim-icons icon-bullet-list-67"></i>
                                <p>{{ __('User Management') }}</p>
                            </a>
                        </li>
                    </ul>
                </div>
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
            <li @if ($pageSlug == 'notifications') class="active " @endif>
                <a href="{{ route('pages.notifications') }}">
                <i class="tim-icons icon-paper"></i>
                    <h4>{{ __('notifications') }}</h4>
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
            <li @if ($pageSlug == 'rtl') class="active " @endif>
                <a href="{{ route('pages.rtl') }}">
                    <i class="tim-icons icon-chart-bar-32"></i>
                    <h4>{{ __('Analytics') }}</h4>
                </a>
            </li>
            <li class=" {{ $pageSlug == 'upgrade' ? 'active' : '' }} bg-info">
                <a href="{{ route('pages.upgrade') }}">
                    <i class="tim-icons icon-spaceship"></i>
                    <p>{{ __('Upgrade to PRO') }}</p>
                </a>
            </li>
        </ul>
    </div>
</div>
