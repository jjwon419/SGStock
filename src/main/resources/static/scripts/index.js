function onBuyButton(companyId){
    fetch("/api/buy", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            companyId: companyId,
            count: document.getElementById('company_' + companyId).value
        })
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(data => {
                    throw new Error(data.message || '매수를 실패하였습니다.');
                });
            }
            return response.json();   // Parse JSON from the response
        })
        .then(data => {
            alert(data.message);
            if(data.statusCode === 0){
                location.reload(true);
            }
        })
        .catch(error => {
            alert(error.message);
        });
}

function onSellButton(companyId){
    fetch("/api/sell", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            companyId: companyId,
            count: document.getElementById('companOwned_' + companyId).value
        })
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(data => {
                    throw new Error(data.message || '매도를 실패하였습니다.');
                });
            }
            return response.json();   // Parse JSON from the response
        })
        .then(data => {
            alert(data.message);
            if(data.statusCode === 0){
                location.reload(true);
            }
        })
        .catch(error => {
            alert(error.message);
        });
}