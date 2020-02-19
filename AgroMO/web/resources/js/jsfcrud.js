function handleSubmit(args, dialog) {
    if (args.validationFailed) {
        PF(dialog).jq.effect('shake', {times: 3}, 100);
    } else {
        PF(dialog).hide();
    }
}

function handleSubmitOpen(args, dialog) {
    if (args.validationFailed) {
        PF(dialog).jq.effect('shake', {times: 3}, 100);
    } else {
        PF(dialog).show();
    }
}