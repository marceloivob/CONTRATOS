 
   set local contratos.cpf_usuario TO 'SS3460524';
   UPDATE contratos.contrato SET dt_fim_vigencia_original = fim_vigencia, versao = versao+1 where id > 0;
   
 
  
