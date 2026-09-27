let systems=[];
const $=id=>document.getElementById(id);

async function request(url,options={}){
    const response=await fetch(url,options);
    if(!response.ok) throw new Error(await response.text()||`Request failed: ${response.status}`);
    return response.json();
}

async function load(){
    try{
        const [data,stats]=await Promise.all([
            request('/api/cooling'),
            request('/api/cooling/dashboard/stats')
        ]);
        systems=data;
        $('buildings').textContent=stats.buildings;
        $('demand').textContent=Math.round(stats.demand)+' kW';
        $('storage').textContent=Math.round(stats.storage)+'%';
        $('cost').textContent='₹'+Math.round(stats.cost).toLocaleString('en-IN');
        render();
    }catch(error){
        console.error(error);
        $('rows').innerHTML='<tr><td colspan="6">Unable to load cooling data. Check MySQL and the Spring Boot server.</td></tr>';
        $('systemCards').innerHTML='<p>Unable to load systems.</p>';
        $('storageCards').innerHTML='<p>Unable to load thermal storage data.</p>';
        $('energyCards').innerHTML='<p>Unable to load energy data.</p>';
    }
}

function status(d){return d<100?'Low':d<250?'Normal':'High'}
function percent(s){return s.storageCapacity>0?Math.min(100,Math.max(0,s.storedEnergy/s.storageCapacity*100)):0}

function render(){
    $('rows').innerHTML=systems.map(s=>{
        const p=percent(s);
        return `<tr><td><b>${s.buildingName}</b><br><small>${s.systemName}</small></td><td>${s.coolingDemand} kW</td><td><div class="bar"><i style="width:${p}%"></i></div>${Math.round(p)}%</td><td>${s.energyConsumption} kWh</td><td><span class="badge ${status(s.coolingDemand).toLowerCase()}">${status(s.coolingDemand)}</span></td><td><button onclick="editSystem(${s.id})">Edit</button> <button onclick="removeSystem(${s.id})">Delete</button></td></tr>`;
    }).join('');

    $('systemCards').innerHTML=systems.map(s=>`<div class="system"><h4>${s.buildingName}</h4><p>${s.systemName}</p><b>${s.coolingDemand} kW</b><p>Outdoor: ${s.outdoorTemperature}°C</p><span class="badge ${status(s.coolingDemand).toLowerCase()}">${status(s.coolingDemand)} Demand</span></div>`).join('')||'<p>No cooling systems found.</p>';

    $('storageCards').innerHTML=systems.map(s=>{
        const p=percent(s);
        return `<div class="system"><h4>${s.buildingName}</h4><p>${s.storedEnergy} / ${s.storageCapacity} kWh</p><div class="bar"><i style="width:${p}%"></i></div><p>Storage level: <b>${Math.round(p)}%</b></p><div class="actions"><button onclick="updateStorage(${s.id},'charge')">Charge +50</button><button onclick="updateStorage(${s.id},'discharge')">Discharge -50</button></div></div>`;
    }).join('')||'<p>No thermal storage systems found.</p>';

    $('energyCards').innerHTML=systems.map(s=>`<div class="system"><h4>${s.buildingName}</h4><p>Consumption: <b>${s.energyConsumption} kWh</b></p><p>Rate: ₹${s.electricityRate}/kWh</p><p>Estimated cost: <b>₹${(s.energyConsumption*s.electricityRate).toFixed(2)}</b></p></div>`).join('')||'<p>No energy data found.</p>';
}

function showSection(x){
    document.querySelectorAll('.section').forEach(e=>e.classList.add('hidden'));
    $(x).classList.remove('hidden');
    $('title').textContent=x==='dashboard'?'Dashboard Overview':x==='thermal-storage'?'Thermal Storage':x.replace(/^./,c=>c.toUpperCase());
}

function openForm(s){
    $('modal').classList.remove('hidden');
    $('formTitle').textContent=s?'Edit Cooling System':'Add Cooling System';
    if(s){
        ['id','buildingName','systemName','coolingDemand','outdoorTemperature','storageCapacity','storedEnergy','energyConsumption','electricityRate'].forEach(k=>$(k).value=s[k]??'');
    }else{
        $('coolingForm').reset();
        $('id').value='';
    }
}

function closeForm(){$('modal').classList.add('hidden')}
function editSystem(id){openForm(systems.find(s=>s.id===id))}

async function saveSystem(e){
    e.preventDefault();
    const data={
        buildingName:$('buildingName').value.trim(),
        systemName:$('systemName').value.trim(),
        coolingDemand:+$('coolingDemand').value,
        outdoorTemperature:+$('outdoorTemperature').value,
        storageCapacity:+$('storageCapacity').value,
        storedEnergy:+$('storedEnergy').value,
        energyConsumption:+$('energyConsumption').value,
        electricityRate:+$('electricityRate').value
    };
    if(data.storageCapacity<=0||data.storedEnergy<0||data.storedEnergy>data.storageCapacity){
        alert('Stored energy must be between 0 and the storage capacity.');
        return;
    }
    try{
        const currentId=$('id').value;
        const url=currentId?'/api/cooling/'+currentId:'/api/cooling';
        await request(url,{method:currentId?'PUT':'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify(data)});
        closeForm();
        await load();
    }catch(error){
        console.error(error);
        alert('Could not save the cooling system. Check the server and database.');
    }
}

async function removeSystem(i){
    if(!confirm('Delete this cooling system?')) return;
    try{
        await request('/api/cooling/'+i,{method:'DELETE'});
        await load();
    }catch(error){
        console.error(error);
        alert('Could not delete the cooling system.');
    }
}

async function updateStorage(i,type){
    try{
        await request(`/api/cooling/${i}/${type}?amount=50`,{method:'POST'});
        await load();
    }catch(error){
        console.error(error);
        alert('Could not update thermal storage.');
    }
}

document.addEventListener('DOMContentLoaded', load);
