const AUTH_TOKEN = 'DRBOT_AUTH_TOKEN';

const commonError = (xhr, textStatus, errorThrown) =>{
    if(xhr.responseText == null || xhr.responseText == undefined || xhr.responseText == ''){
        alert("서버와의 연결에 실패했습니다. " + "에러[" + textStatus + "]" + errorThrown   );
    }else{
        let response = JSON.parse(xhr.responseText);
        alert( response.message  );
    }
}

const validateToken = () => {
    const token = Cookies.get(AUTH_TOKEN);
    if(token != null &&  token != '') return true;
    return false;
}

const getMyInfo = () => {
    return new Promise((resolve, reject) => {
            const url = '/auth/login-info';
                let promise = $.ajax({
                    url: url,
                    type: 'GET'
                }).done(result => {
                    resolve(result);
                }).fail(commonError);
            });
}
