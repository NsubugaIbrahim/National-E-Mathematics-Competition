<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\Request;

class ParticipantsModel extends Model
{
    use HasFactory;

    protected $table = 'participants';

    static public function getSingle($id)
    {
        return self::where('id', $id)->where('is_delete', 0)->first();
    }

    static public function getRecord()
    {
        $return = self::select('participants.*', 'users.name as created_name')
            ->join('users', 'users.id', '=', 'participants.created_by');

        if (!empty(Request::get('username'))) {
            $return = $return->where('participants.username', 'like', '%' . Request::get('username') . '%');
        }

        if (!empty(Request::get('firstName'))) {
            $return = $return->where('participants.firstName', 'like', '%' . Request::get('firstName') . '%');
        }

        if (!empty(Request::get('lastName'))) {
            $return = $return->where('participants.lastName', 'like', '%' . Request::get('lastName') . '%');
        }

        if (!empty(Request::get('school_regNo'))) {
            $return = $return->where('participants.school_regNo', 'like', '%' . Request::get('school_regNo') . '%');
        }

        $return = $return->where('participants.is_delete', 0)
            ->orderBy('participants.id', 'desc')
            ->paginate(2);
        return $return;
    }

    static public function getExam()
    {
        $return = self::select('participants.*')
            ->join('users', 'users.id', '=', 'participants.created_by')
            ->where('participants.is_delete', 0)
            ->orderBy('participants.name', 'asc')
            ->get();
        return $return;
    }
}
