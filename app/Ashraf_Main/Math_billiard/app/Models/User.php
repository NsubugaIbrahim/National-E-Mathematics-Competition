<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;
use Laravel\Sanctum\HasApiTokens;
use Illuminate\Support\Facades\Request; // Correct import for Request facade

class User extends Authenticatable
{
    use HasApiTokens, HasFactory, Notifiable;

    /**
     * The attributes that are mass assignable.
     *
     * @var array<int, string>
     */
    protected $fillable = [
        'name',
        'email',
        'password',
    ];

    /**
     * The attributes that should be hidden for serialization.
     *
     * @var array<int, string>
     */
    protected $hidden = [
        'password',
        'remember_token',
    ];

    /**
     * The attributes that should be cast to native types.
     *
     * @var array<string, string>
     */
    protected $casts = [
        'email_verified_at' => 'datetime',
    ];

    static public function getSingle($id)
    {
        return self::find($id);
    }

    static public function getAdmin()
    {
        $query = self::select('users.*')
                    ->where('user_type', 1)
                    ->where('is_delete', 0);

                    if (!empty(Request::get('name'))) {
                        $query->where('name','like',"%" .Request::get('name'). '%');
                    }

                    if (!empty(Request::get('email'))) {
                        $query->where('email','like',"%" .Request::get('email'). '%');
                    }

                    if (!empty(Request::get('date'))) {
                        $query->whereDate('created_at','=', Request::get('date'));
                    }

        return $query->orderBy('id', 'desc')
                    ->paginate(3);
    }

    static public function getSchooL()
    {
        $query = self::select('users.*')
                    ->where('user_type', 4)
                    ->where('is_delete', 0);

                    if (!empty(Request::get('name'))) {
                        $query->where('name','like',"%" .Request::get('name'). '%');
                    }

                    if (!empty(Request::get('last_name'))) {
                        $query->where('last_name','like',"%" .Request::get('last_name'). '%');
                    }

                    if (!empty(Request::get('email'))) {
                        $query->where('email','like',"%" .Request::get('email'). '%');
                    }

                    if (!empty(Request::get('address'))) {
                        $query->where('address','like',"%" .Request::get('address'). '%');
                    }

                    return $query->orderBy('id', 'desc')
                    ->paginate(2);
   
    }

    static public function getSchooL_Rep()
    {
        $query = self::select('users.*')
                    ->where('user_type', 2)
                    ->where('is_delete', 0);

                    if (!empty(Request::get('name'))) {
                        $query->where('name','like',"%" .Request::get('name'). '%');
                    }

                    if (!empty(Request::get('last_name'))) {
                        $query->where('last_name','like',"%" .Request::get('last_name'). '%');
                    }

                    if (!empty(Request::get('email'))) {
                        $query->where('email','like',"%" .Request::get('email'). '%');
                    }

                    if (!empty(Request::get('address'))) {
                        $query->where('address','like',"%" .Request::get('address'). '%');
                    }

                    return $query->orderBy('id', 'desc')
                    ->paginate(2);
   
    }

    static public function getStudent()
    {
        $query = self::select('users.*')
                    ->where('users.user_type', 3)
                    ->where('users.is_delete', 0);
        return $query->orderBy('users.id', 'desc')
                    ->paginate(4);
    }

    static public function getEmailSingle($email)
    {
        return self::where('email', $email)->first();
    }

    static public function getTokenSingle($remember_token)
    {
        return self::where('remember_token', $remember_token)->first();
    }

    // In app/Models/User.php

    public function getFullNameAttribute()
    {
        return $this->name . ' ' . $this->last_name;
    }

    public function getProfile()
    {
        if(!empty($this->profile_pic) && file_exists('upload/profile/' .$this->profile_pic))
        {
            return url('upload/profile/' .$this->profile_pic);
        }
        else
        {
            return "";
        }
    }

    public function getSearchStudent($params)
    {
        $query = self::query()->where('user_type', 2);

        if (!empty($params['id'])) {
            $query->where('id', $params['id']);
        }

        if (!empty($params['name'])) {
            $query->where('name', 'like', '%' . $params['name'] . '%');
        }

        if (!empty($params['last_name'])) {
            $query->where('last_name', 'like', '%' . $params['last_name'] . '%');
        }

        if (!empty($params['email'])) {
            $query->where('email', 'like', '%' . $params['email'] . '%');
        }

        if (!empty($params['address'])) {
            $query->where('address', 'like', '%' . $params['address'] . '%');
        }

        return $query->get();
    }

    // Other methods like getSingle, getAdmin, getSchooL_Rep, etc., can remain static if they don't require instance-specific data.



}


