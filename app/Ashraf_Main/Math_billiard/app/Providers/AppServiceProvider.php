<?php

namespace App\Providers;

use Illuminate\Support\ServiceProvider;
use Illuminate\Support\Facades\Schema;
use Illuminate\Support\Facades\URL;
use Illuminate\Pagination\Paginator;

class AppServiceProvider extends ServiceProvider
{
    /**
     * Register any application services.
     */
    public function register(): void
    {
        // You can register any application services here.
    }

    /**
     * Bootstrap any application services.
     */
    public function boot(): void
    {
        // Use Bootstrap for pagination styling
        Paginator::useBootstrap();

        // Set a default string length for database migrations (for compatibility with older MySQL versions)
        Schema::defaultStringLength(191);

        // Force HTTPS if your application should be using it
        if ($this->app->environment('production')) {
            URL::forceScheme('https');
        }
    }
}
